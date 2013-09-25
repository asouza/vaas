package br.com.caelum.vraptor.vaas.authentication;

import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
public class JAASProvider implements AuthProvider {

	@Inject
	private HttpServletRequest httpRequest;

	@Override
	public Principal authenticate(String login, String password) throws Exception {
		httpRequest.login(httpRequest.getParameter("login"),
				httpRequest.getParameter("password"));
		return httpRequest.getUserPrincipal();
	}

}
