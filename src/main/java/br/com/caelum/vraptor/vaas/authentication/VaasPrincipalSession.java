package br.com.caelum.vraptor.vaas.authentication;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import br.com.caelum.vraptor.vaas.event.AuthenticatedEvent;
import br.com.caelum.vraptor.vaas.event.LogoutEvent;

@ApplicationScoped
public class VaasPrincipalSession {

	private boolean logged;

	public void handle(@Observes AuthenticatedEvent vent){
		this.logged = true;
	}
	
	public void handle(@Observes LogoutEvent event){
		this.logged = false;
	}
	
	public boolean isLogged() {
		return logged;
	}
	
}
