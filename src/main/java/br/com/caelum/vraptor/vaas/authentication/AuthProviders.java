package br.com.caelum.vraptor.vaas.authentication;

import java.util.ArrayList;
import java.util.List;


public class AuthProviders {

	private List<Class<? extends AuthProvider>> providers = new ArrayList<Class<? extends AuthProvider>>();

	public AuthProviders(Class<? extends AuthProvider> initialProvider) {
		providers.add(initialProvider);
	}
	
	public List<Class<? extends AuthProvider>> get() {
		return providers;
	}

}
