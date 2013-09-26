package br.com.caelum.vraptor.vaas.configurations;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.vaas.Rule;
import br.com.caelum.vraptor.vaas.authentication.VaasPrincipalSession;

@RequestScoped
public class LoggedRule implements Rule{

	@Inject
	private VaasPrincipalSession principalSession;

	@Override
	public boolean isAuthorized() {
		return principalSession.isLogged();
	}

	
}
