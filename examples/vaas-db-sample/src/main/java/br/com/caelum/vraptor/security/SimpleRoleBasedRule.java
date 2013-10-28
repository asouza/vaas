package br.com.caelum.vraptor.security;

import java.util.Set;

import javax.enterprise.context.SessionScoped;

import br.com.caelum.vraptor.model.Role;
import br.com.caelum.vraptor.model.User;
import br.com.caelum.vraptor.vaas.Rule;

@SessionScoped
public class SimpleRoleBasedRule implements Rule {
	

	private User loggedUser;
	private Set<Role> rolesAllowed;


	public SimpleRoleBasedRule(User loggedUser, Set<Role> rolesAllowed) {
		this.loggedUser = loggedUser;
		this.rolesAllowed = rolesAllowed;
	}

	@Override
	public boolean isAuthorized() {
		boolean valid = false;
		
		for (Role role : rolesAllowed) {
			valid = valid || loggedUser.getRoles().contains(role);
		}

		return valid;
	}

}
