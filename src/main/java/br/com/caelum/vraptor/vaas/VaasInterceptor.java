package br.com.caelum.vraptor.vaas;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.vaas.authentication.Authenticator;
import br.com.caelum.vraptor.vaas.authentication.VaasPrincipalSession;
import br.com.caelum.vraptor.vaas.authorization.PermissionVerifier;
import br.com.caelum.vraptor.vaas.event.AuthorizationFailedEvent;
import br.com.caelum.vraptor.vaas.event.RefreshUserEvent;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;

@Intercepts
@ApplicationScoped
public class VaasInterceptor implements Interceptor {
	@Inject
	private PermissionVerifier permissions;
	@Inject
	private Authenticator auth;
	@Inject
	private ServletContext context;
	@Inject
	private HttpServletRequest httpRequest;
	@Inject
	private Event<RefreshUserEvent> refreshUserEvent;
	@Inject
	private VaasPrincipalSession principalSession;
	@Inject
	private Event<AuthorizationFailedEvent> authorizationFailedEvent;

	private String loginUrl;
	private String logoutUrl;

	@PostConstruct
	public void config() {
		this.loginUrl = context.getInitParameter("loginUrl");
		this.logoutUrl = context.getInitParameter("logoutUrl");
	}

	@Override
	public void intercept(InterceptorStack stack, ControllerMethod method,
			Object controllerInstance) throws InterceptionException {
		String context = this.context.getContextPath();
		String uri = httpRequest.getRequestURI().substring(context.length());

		if (uri.equals(loginUrl)) {
			auth.tryToLogin();
			return;
		}

		if (uri.equals(logoutUrl)) {
			auth.tryToLogout();
			return;
		}

		List<Rule> rulesNotAllowed = permissions.verifyAccessFor(uri);
		if (rulesNotAllowed.isEmpty()) {
			maybeRefreshUser();
			authorizationFailedEvent.fire(new AuthorizationFailedEvent(
					rulesNotAllowed));
		} else {
			maybeRefreshUser();
			stack.next(method, controllerInstance);
		}

	}

	private void maybeRefreshUser() {
		if (principalSession.isLogged()) {
			refreshUserEvent.fire(new RefreshUserEvent());
		}
	}

	@Override
	public boolean accepts(ControllerMethod method) {
		return true;
	}

}
