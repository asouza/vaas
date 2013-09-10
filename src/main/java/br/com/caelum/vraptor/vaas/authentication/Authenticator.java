package br.com.caelum.vraptor.vaas.authentication;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.vaas.event.AuthenticateFailedEvent;
import br.com.caelum.vraptor.vaas.event.AuthenticatedEvent;
import br.com.caelum.vraptor.vaas.event.LogoutEvent;

public class Authenticator {
	@Inject private HttpServletRequest httpRequest;
	@Inject private Event<AuthenticatedEvent> authenticatedEvent;
	@Inject private Event<AuthenticateFailedEvent> authenticationFailedEvent;
	@Inject private Event<LogoutEvent> logoutEvent;

	public void tryToLogin() {
		try {
			httpRequest.login(httpRequest.getParameter("login"),
					httpRequest.getParameter("password"));
			authenticatedEvent.fire(new AuthenticatedEvent(httpRequest
					.getUserPrincipal()));
		} catch (ServletException e) {
			authenticationFailedEvent.fire(new AuthenticateFailedEvent(e));
		}
	}
	
	public void tryToLogout(){
		logoutEvent.fire(new LogoutEvent());
		return;
	}
}
