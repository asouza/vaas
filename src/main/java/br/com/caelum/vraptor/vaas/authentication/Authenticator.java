package br.com.caelum.vraptor.vaas.authentication;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.vaas.InstanceProviderList;
import br.com.caelum.vraptor.vaas.event.AuthenticationFailedEvent;
import br.com.caelum.vraptor.vaas.event.AuthenticatedEvent;
import br.com.caelum.vraptor.vaas.event.LogoutEvent;

@ApplicationScoped
public class Authenticator {

	private static Logger logger = LoggerFactory.getLogger(Authenticator.class);

	@Inject private HttpServletRequest httpRequest;
	@Inject private Event<AuthenticatedEvent> authenticatedEvent;
	@Inject private Event<AuthenticationFailedEvent> authenticationFailedEvent;
	@Inject private Event<LogoutEvent> logoutEvent;
	@Inject @InstanceProviderList private List<Instance<AuthProvider>> providers;

	public void tryToLogin() {
		Principal principal = null;
		Iterator<Instance<AuthProvider>> iterator = providers.iterator();
		AuthenticationFailedEvent event = new AuthenticationFailedEvent();

		//for while, if one provider returns Principal, is ok.
		while(iterator.hasNext() && principal==null){
			try {
				AuthProvider provider = iterator.next().get();
				logger.debug("trying to login using {}", provider);
				principal = provider.authenticate(httpRequest.getParameter("login"),httpRequest.getParameter("password"));
				
			} catch (Exception e) {
				event.add(e);
			}
		}

		if(principal != null){
			logger.debug("Login successful, firing the AuthenticatedEvent event");
			authenticatedEvent.fire(new AuthenticatedEvent(principal));
		}else{
			logger.debug("Login failed, firing the AuthenticateFailedEvent event");
			authenticationFailedEvent.fire(event);
		}
			
	}
	
	public void tryToLogout(){
		logger.debug("Firing the LogoutEvent event");
		logoutEvent.fire(new LogoutEvent());
	}
}
