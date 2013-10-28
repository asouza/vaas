package br.com.caelum.vraptor.security;

import java.security.Principal;
import java.util.Set;

import javax.enterprise.context.SessionScoped;

import br.com.caelum.vraptor.model.Role;
import br.com.caelum.vraptor.model.User;
import br.com.caelum.vraptor.vaas.Rule;
import br.com.caelum.vraptor.vaas.authentication.VaasSession;

@SessionScoped
public class SimpleRoleBasedRule implements Rule {
	
	private VaasSession userSession;
	private Set<Role> rolesAllowed;


	public SimpleRoleBasedRule(VaasSession userSession, Set<Role> rolesAllowed) {
		this.userSession = userSession;
		this.rolesAllowed = rolesAllowed;
	}

	@Override
	public boolean isAuthorized() {
		
		if(rolesAllowed.isEmpty()){
			return true;
		}
		
		boolean valid = false;

		if(userSession.isLogged()){
			User user = (User) userSession.getLoogedUser();	
			
			for (Role role : rolesAllowed) {
				valid = valid || user.getRoles().contains(role);
			}
		}

		return valid;
	}

}
