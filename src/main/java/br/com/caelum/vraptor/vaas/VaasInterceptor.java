package br.com.caelum.vraptor.vaas;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.vaas.event.AuthenticateFailedEvent;
import br.com.caelum.vraptor.vaas.event.AuthenticatedEvent;
import br.com.caelum.vraptor.vaas.event.AuthorizationFailedEvent;
import br.com.caelum.vraptor.vaas.event.LogoutEvent;
import br.com.caelum.vraptor.vaas.event.RefreshUserEvent;
import br.com.caelum.vraptor4.InterceptionException;
import br.com.caelum.vraptor4.Intercepts;
import br.com.caelum.vraptor4.controller.ControllerMethod;
import br.com.caelum.vraptor4.core.InterceptorStack;
import br.com.caelum.vraptor4.interceptor.Interceptor;

@Intercepts
@ApplicationScoped
public class VaasInterceptor implements Interceptor {

	@Inject
	private Event<AuthenticatedEvent> authenticatedEvent;

	@Inject
	private Event<AuthenticateFailedEvent> authenticationFailedEvent;

	@Inject
	private Event<AuthorizationFailedEvent> authorizationFailedEvent;

	@Inject
	private Event<RefreshUserEvent> refreshUserEvent;

	@Inject
	private Event<LogoutEvent> logoutEvent;

	private String loginUrl;

	private String logoutUrl;

	private Object accessConfiguration;

	private RolesConfigMethod rulesConfigMethod;
	
	@Inject
	private ServletContext context;
	
	@Inject
	private HttpServletRequest httpRequest;

	@SuppressWarnings({ "rawtypes", "serial" })
	@PostConstruct
	public void config() {
		this.loginUrl = context.getInitParameter("loginUrl");
		this.logoutUrl = context.getInitParameter("logoutUrl");
		Instance possibleConfigurations = CDI.current().select(new AnnotationLiteral<AccessConfiguration>() {});
		if (possibleConfigurations.isAmbiguous()) {
			throw new RuntimeException(
					"You should use just one AccessConfiguration class");
		}
		this.accessConfiguration = possibleConfigurations.get();
		this.rulesConfigMethod = new RolesConfigMethod(this.accessConfiguration);
	}


	@Override
	public void intercept(InterceptorStack stack, ControllerMethod method,
			Object controllerInstance) throws InterceptionException {
		String context = httpRequest.getServletContext().getContextPath();
		String uri = httpRequest.getRequestURI().substring(context.length());
		
		if (uri.equals(logoutUrl)) {
			logoutEvent.fire(new LogoutEvent());
			return;
		}
		
		if (uri.equals(loginUrl)) {
			try {
				httpRequest.login(httpRequest.getParameter("login"),
						httpRequest.getParameter("password"));
				authenticatedEvent.fire(new AuthenticatedEvent(httpRequest
						.getUserPrincipal()));
			} catch (ServletException e) {
				authenticationFailedEvent.fire(new AuthenticateFailedEvent(e));
			}
			return;
		}

		List<Rule> rulesNotAllowed = getNotAllowedRules(rulesConfigMethod.rulesFor(uri));
		if (!rulesNotAllowed.isEmpty()) {
			authorizationFailedEvent.fire(new AuthorizationFailedEvent(rulesNotAllowed));
			return ;
		}
		
		refreshUserEvent.fire(new RefreshUserEvent());
		
		stack.next(method, controllerInstance);
	}


	private List<Rule> getNotAllowedRules(List<Rule> roles) {
		List<Rule> rulesNotAllowed = new ArrayList<Rule>();
		for (Rule rule : roles) {
			if(!rule.isAuthorized()){
				rulesNotAllowed.add(rule);
			}
		}
		return rulesNotAllowed;
	}

	@Override
	public boolean accepts(ControllerMethod method) {
		return true;
	}
	


}
