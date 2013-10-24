package br.com.caelum.vraptor.vaas.configurations;

import java.util.Arrays;

import javax.enterprise.inject.Vetoed;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.vaas.Rule;

@Vetoed
public class JAASRolesRule implements Rule {

	private HttpServletRequest request;
	private String[] roles;

	public JAASRolesRule(HttpServletRequest request,String... roles) {
		this.request = request;
		this.roles = roles;
	}

	@Override
	public boolean isAuthorized() {
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
		JAASRolesRule other = (JAASRolesRule) obj;
		if (!Arrays.equals(roles, other.roles))
			return false;
		return true;
	}
	
	

}