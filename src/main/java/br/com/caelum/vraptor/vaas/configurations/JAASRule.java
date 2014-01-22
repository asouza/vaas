package br.com.caelum.vraptor.vaas.configurations;

import java.util.Arrays;

import javax.enterprise.inject.Vetoed;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.vaas.Rule;

@Vetoed
public class JAASRule implements Rule {

	private String[] roles;

	private JAASRule(String...roles) {
	}
	
	public static JAASRule withRoles(String...roles) {
		return new JAASRule(roles);
	}

	@Override
	public boolean isAuthorized(HttpServletRequest request) {
		boolean valid = false;
		for (String role : roles) {
			valid = valid || request.isUserInRole(role);
		}

		return valid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(roles);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JAASRule other = (JAASRule) obj;
		if (!Arrays.equals(roles, other.roles))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JAASRolesRule [roles=" + Arrays.toString(roles) + "]";
	}
	
	
	
	

}
