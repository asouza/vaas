package br.com.caelum.vraptor.vaas.authentication;

import java.security.Principal;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.vaas.event.AuthenticateFailedEvent;
import br.com.caelum.vraptor.vaas.event.AuthenticatedEvent;
import br.com.caelum.vraptor.vaas.event.LogoutEvent;

@RequestScoped
public class Authenticator {
	@Inject private HttpServletRequest httpRequest;
	@Inject private Event<AuthenticatedEvent> authenticatedEvent;
	@Inject private Event<AuthenticateFailedEvent> authenticationFailedEvent;
	@Inject private Event<LogoutEvent> logoutEvent;
	@Inject private Instance<AuthProvider> providers;
	
	@PostConstruct
	public void configure(){
		
	}

	public void tryToLogin() {
		try {
			Principal principal = null;
			Iterator<AuthProvider> iterator = providers.iterator();
			//for while, if one provider returns Principal, is ok.
			while(iterator.hasNext() && principal==null){
				AuthProvider provider = iterator.next();
				principal = provider.authenticate(httpRequest.getParameter("login"), 
						httpRequest.getParameter("password"));				
			}
			authenticatedEvent.fire(new AuthenticatedEvent(principal));
		} catch (Exception e) {
			authenticationFailedEvent.fire(new AuthenticateFailedEvent(e));
		}
	}
	
	public void tryToLogout(){
		logoutEvent.fire(new LogoutEvent());
		return;
	}
}
