package br.com.caelum.vraptor.vaas.event;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import br.com.caelum.vraptor.vaas.Rule;

@RequestScoped
public class AuthorizationFailedEvent {


	private List<Rule> rolesNotAllowed;

	public AuthorizationFailedEvent(List<Rule> rolesNotAllowed) {
		this.rolesNotAllowed = rolesNotAllowed;
	}
	
	public List<Rule> getRolesNotAllowed() {
		return rolesNotAllowed;
	}

}
