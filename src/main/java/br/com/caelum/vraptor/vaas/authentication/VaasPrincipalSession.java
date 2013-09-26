package br.com.caelum.vraptor.vaas.authentication;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;

import br.com.caelum.vraptor.vaas.event.AuthenticatedEvent;
import br.com.caelum.vraptor.vaas.event.LogoutEvent;

/**
 * Keep information about the logged user
 * @author Alberto Souza
 *
 */
@SessionScoped
public class VaasPrincipalSession implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -27943664743662189L;
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
