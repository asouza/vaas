package br.com.caelum.vraptor.vaas.authentication;

import java.io.Serializable;
import java.security.Principal;

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
public class VaasPrincipalSession implements VaasSession, Serializable{

	private static final long serialVersionUID = -27943664743662189L;
	private boolean logged;
	private Principal loogedUser;

	public void handle(@Observes AuthenticatedEvent event){
		this.logged = true;
		loogedUser = event.getUserPrincipal();
	}
	
	public void handle(@Observes LogoutEvent event){
		this.logged = false;
		loogedUser = null;
	}
	
	/* (non-Javadoc)
	 * @see br.com.caelum.vraptor.vaas.authentication.VaasSession#isLogged()
	 */
	@Override
	public boolean isLogged() {
		return logged;
	}
	
	/* (non-Javadoc)
	 * @see br.com.caelum.vraptor.vaas.authentication.VaasSession#getLoogedUser()
	 */
	@Override
	public Principal getLoogedUser() {
		return loogedUser;
	}
	
}
