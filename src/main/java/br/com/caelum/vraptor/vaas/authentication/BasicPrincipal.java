package br.com.caelum.vraptor.vaas.authentication;

import java.security.Principal;

public class BasicPrincipal<T> implements Principal {

	private String login;
	private T delegate;

	public BasicPrincipal(String login, T delegate) {
		super();
		this.login = login;
		this.delegate = delegate;
	}

	@Override
	public String getName() {
		return login;
	}
	
	public T getLogged() {
		return delegate;
	}

}
