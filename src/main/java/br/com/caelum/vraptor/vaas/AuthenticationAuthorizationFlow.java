package br.com.caelum.vraptor.vaas;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.vaas.authentication.Authenticator;
import br.com.caelum.vraptor.vaas.authentication.VaasSession;
import br.com.caelum.vraptor.vaas.authorization.PermissionVerifier;
import br.com.caelum.vraptor.vaas.configurations.WebXmlConfigurationGetter;
import br.com.caelum.vraptor.vaas.event.AuthorizationFailedEvent;
import br.com.caelum.vraptor.vaas.event.RefreshUserEvent;

/**
 * This flow is the basis of VAAS. Any framework which wants to use VAAS, should 
 * use this object and just implement its way of continue the request flow.
 * @author Alberto Souza
 *
 */
@ApplicationScoped
public class AuthenticationAuthorizationFlow {

	protected static final String LOGOUT_URL_PARAMETER = "logoutUrl";
	protected static final String LOGIN_URL_PARAMETER = "loginUrl";
	protected static final String DEFAULT_LOGIN_URI = "/login";
	protected static final String DEFAULT_LOGOUT_URI = "/logout";
	private final PermissionVerifier permissions;
	private final Authenticator auth;
	private final ServletContext context;
	private final HttpServletRequest httpRequest;
	private final Event<RefreshUserEvent> refreshUserEvent;
	private final VaasSession principalSession;
	private final Event<AuthorizationFailedEvent> authorizationFailedEvent;
	private final WebXmlConfigurationGetter config;

	private String loginUrl;
	private String logoutUrl;
	
	/** @deprecated CDI eyes only*/
	protected AuthenticationAuthorizationFlow() {
		this(null, null, null, null, null, null, null, null);
	}
	
	@Inject
	public AuthenticationAuthorizationFlow(PermissionVerifier permissions,
			Authenticator auth, ServletContext context,
			HttpServletRequest httpRequest,
			Event<RefreshUserEvent> refreshUserEvent,
			VaasSession principalSession,
			Event<AuthorizationFailedEvent> authorizationFailedEvent,
			WebXmlConfigurationGetter config) {
		this.permissions = permissions;
		this.auth = auth;
		this.context = context;
		this.httpRequest = httpRequest;
		this.refreshUserEvent = refreshUserEvent;
		this.principalSession = principalSession;
		this.authorizationFailedEvent = authorizationFailedEvent;
		this.config = config;
	}

	@PostConstruct
	public void config() {
		this.loginUrl = config.getOrDefault(DEFAULT_LOGIN_URI, LOGIN_URL_PARAMETER);
		this.logoutUrl = config.getOrDefault(DEFAULT_LOGOUT_URI, LOGOUT_URL_PARAMETER);
	}

	public void intercept(Runnable frameworkFlow) {
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
		
		maybeRefreshUser();
		if (!rulesNotAllowed.isEmpty()) {
			authorizationFailedEvent.fire(new AuthorizationFailedEvent(
					rulesNotAllowed));
		} else {
			frameworkFlow.run();
		}

	}

	private void maybeRefreshUser() {
		if (principalSession.isLogged()) {
			refreshUserEvent.fire(new RefreshUserEvent());
		}
	}
}
