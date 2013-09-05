package br.com.caelum.vraptor.vaas.event;

import java.util.List;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AuthorizationFailedEvent {


	private List<String> rolesNotAllowed;

	public AuthorizationFailedEvent(List<String> rolesNotAllowed) {
		this.rolesNotAllowed = rolesNotAllowed;
	}
	
	public List<String> getRolesNotAllowed() {
		return rolesNotAllowed;
	}

}
