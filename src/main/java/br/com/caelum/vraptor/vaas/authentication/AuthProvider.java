package br.com.caelum.vraptor.vaas.authentication;

import java.security.Principal;

public interface AuthProvider {

	/**
	 * 
	 * @param login
	 * @param password
	 * @return object that represents the User loaded
	 * @throws Exception to be used inside VAAS
	 */
	public Principal authenticate(String login,String password) throws Exception;
}
