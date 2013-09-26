package br.com.caelum.vraptor.vaas.authentication;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.vaas.ConfigurationFinder;
import br.com.caelum.vraptor.vaas.InstanceProviderList;
import br.com.caelum.vraptor.vaas.ProviderConfiguration;
import br.com.caelum.vraptor.vaas.AuthProviders;
import br.com.caelum.vraptor.vaas.event.AuthenticateFailedEvent;
import br.com.caelum.vraptor.vaas.event.AuthenticatedEvent;
import br.com.caelum.vraptor.vaas.event.LogoutEvent;

@ApplicationScoped
public class Authenticator {
	@Inject private HttpServletRequest httpRequest;
	@Inject private Event<AuthenticatedEvent> authenticatedEvent;
	@Inject private Event<AuthenticateFailedEvent> authenticationFailedEvent;
	@Inject private Event<LogoutEvent> logoutEvent;
	@Inject @InstanceProviderList private List<Instance<AuthProvider>> providers;

	public void tryToLogin() {
		try {
			Principal principal = null;
			Iterator<Instance<AuthProvider>> iterator = providers.iterator();
			//for while, if one provider returns Principal, is ok.
			while(iterator.hasNext() && principal==null){
				AuthProvider provider = iterator.next().get();
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
