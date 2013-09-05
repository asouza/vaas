package br.com.caelum.vraptor.vaas.event;

import java.security.Principal;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AuthenticatedEvent {

	private Principal userPrincipal;

	public AuthenticatedEvent(Principal userPrincipal) {
		this.userPrincipal = userPrincipal;
	}
	
	public Principal getUserPrincipal() {
		return userPrincipal;
	}

}
