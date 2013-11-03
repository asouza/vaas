package br.com.caelum.vraptor.vaas.configurations;

import java.util.Collection;

import javax.enterprise.context.SessionScoped;

import br.com.caelum.vraptor.vaas.Rule;
import br.com.caelum.vraptor.vaas.authentication.Authenticable;
import br.com.caelum.vraptor.vaas.authentication.VaasSession;
import br.com.caelum.vraptor.vaas.authorization.Role;

@SessionScoped
public class VaasRoleRule implements Rule {
	
	private VaasSession userSession;
	private Collection<Role> rolesAllowed;


	public VaasRoleRule(VaasSession userSession, Collection<Role> rolesAllowed) {
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
			Authenticable user = (Authenticable) userSession.getLoggedUser();	
			
			for (Role role : rolesAllowed) {
				valid = valid || user.getRoles().contains(role);
			}
		}

		return valid;
	}

}