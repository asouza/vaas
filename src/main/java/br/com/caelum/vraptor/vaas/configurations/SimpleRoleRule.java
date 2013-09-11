package br.com.caelum.vraptor.vaas.configurations;

import javax.enterprise.inject.Vetoed;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.vaas.Rule;

@Vetoed
public class SimpleRoleRule implements Rule {

	private HttpServletRequest request;
	private String[] roles;

	public SimpleRoleRule(HttpServletRequest request,String... roles) {
		this.request = request;
		this.roles = roles;
	}

	@Override
	public boolean isAuthorized() {
		boolean valid = true;
		for (String role : roles) {
			valid = valid && request.isUserInRole(role);
		}

		return valid;
	}

}
