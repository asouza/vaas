package br.com.caelum.vraptor.vaas.authentication;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;


public class AuthProviders implements Iterable<Class<? extends AuthProvider>>{

	private List<Class<? extends AuthProvider>> authProviders;

	public AuthProviders(Class<? extends AuthProvider>... authProviders) {
		this.authProviders = Lists.newArrayList(authProviders);
	}
	
	public List<Class<? extends AuthProvider>> get() {
		return authProviders;
	}

	@Override
	public Iterator<Class<? extends AuthProvider>> iterator() {
		return authProviders.iterator();
	}

}
