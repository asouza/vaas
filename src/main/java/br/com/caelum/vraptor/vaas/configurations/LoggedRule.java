package br.com.caelum.vraptor.vaas.configurations;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.vaas.Rule;

@RequestScoped
public class LoggedRule implements Rule{

	@Inject
	private HttpServletRequest httpRequest;

	@Override
	public boolean isAuthorized() {
		return httpRequest.getUserPrincipal() != null;
	}

	
}
