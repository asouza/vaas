package br.com.caelum.vraptor.vaas.authentication;

import java.security.Principal;

public interface VaasSession {

	public abstract boolean isLogged();

	public abstract Principal getLoggedUser();

}