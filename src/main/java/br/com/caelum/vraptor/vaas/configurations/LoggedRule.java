package br.com.caelum.vraptor.vaas.configurations;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.vaas.Rule;
import br.com.caelum.vraptor.vaas.authentication.VaasSession;

@RequestScoped
public class LoggedRule implements Rule{
	
	private static final Logger logger = LoggerFactory.getLogger(LoggedRule.class);

	@Inject
	private VaasSession principalSession;

	@Override
	public boolean isAuthorized(HttpServletRequest request) {
		boolean logged = principalSession.isLogged();
		logger.debug("Is looged: " + logged);
		return logged;
	}

	
}
