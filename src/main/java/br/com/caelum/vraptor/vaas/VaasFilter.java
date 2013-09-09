package br.com.caelum.vraptor.vaas;

import java.io.IOException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.Matcher;
import net.vidageek.mirror.list.dsl.MirrorList;
import br.com.caelum.vraptor.vaas.event.AuthenticateFailedEvent;
import br.com.caelum.vraptor.vaas.event.AuthenticatedEvent;
import br.com.caelum.vraptor.vaas.event.AuthorizationFailedEvent;
import br.com.caelum.vraptor.vaas.event.LogoutEvent;
import br.com.caelum.vraptor.vaas.event.NotLoggedEvent;
import br.com.caelum.vraptor.vaas.event.RefreshUserEvent;
public class VaasFilter implements Filter {

	@Inject
	private Event<AuthenticatedEvent> authenticatedEvent;

	@Inject
	private Event<AuthenticateFailedEvent> authenticationFailedEvent;

	@Inject
	private Event<AuthorizationFailedEvent> authorizationFailedEvent;

	@Inject
	private Event<NotLoggedEvent> notLoggedEvent;

	@Inject
	private Event<RefreshUserEvent> refreshUserEvent;

	@Inject
	private Event<LogoutEvent> logoutEvent;

	private String loginUrl;

	private String logoutUrl;

	private Object accessConfiguration;

	private RolesConfigMethod rolesConfigMethod;

	@Override
	@SuppressWarnings("rawtypes")
	public void init(FilterConfig filterConfig) throws ServletException {
		this.loginUrl = filterConfig.getInitParameter("loginUrl");
		this.logoutUrl = filterConfig.getInitParameter("logoutUrl");
		Instance possibleConfigurations = CDI.current().select(
				AccessConfiguration.class);
		if (possibleConfigurations.isAmbiguous()) {
			throw new RuntimeException(
					"You should use just one AccessConfiguration class");
		}
		this.accessConfiguration = possibleConfigurations.get();
		this.rolesConfigMethod = new RolesConfigMethod(this.accessConfiguration);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (httpRequest.getRequestURI().equals(logoutUrl)) {
			logoutEvent.fire(new LogoutEvent());
		}
		if (httpRequest.getRequestURI().equals(loginUrl)) {
			try {
				httpRequest.login(request.getParameter("login"),
						request.getParameter("password"));
				authenticatedEvent.fire(new AuthenticatedEvent(httpRequest
						.getUserPrincipal()));
			} catch (ServletException e) {
				authenticationFailedEvent.fire(new AuthenticateFailedEvent(e));
			}
			return;
		}

		if (httpRequest.getUserPrincipal() == null) {
			notLoggedEvent.fire(new NotLoggedEvent());
			return;
		}
		List<Rule> roles = rolesConfigMethod.rolesFor(httpRequest.getRequestURI());
		if (!roles.isEmpty()) {
			ArrayList<Rule> rulesNotAllowed = new ArrayList<Rule>();
			for (Rule rule : roles) {
				//TODO: make this a 'and', not a 'or'
				if (rule.isAuthorized()) {
					refreshUserEvent.fire(new RefreshUserEvent());
					chain.doFilter(request, response);
					return;
				}
				rulesNotAllowed.add(rule);
			}
			authorizationFailedEvent.fire(new AuthorizationFailedEvent(
					rulesNotAllowed));
		}


	}

	@Override
	public void destroy() {
	}

}
