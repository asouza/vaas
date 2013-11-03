package br.com.caelum.vraptor.security;

import br.com.caelum.vraptor.vaas.ProviderConfiguration;
import br.com.caelum.vraptor.vaas.authentication.AuthProviders;
import br.com.caelum.vraptor.vaas.authentication.JAASProvider;

public class VaasConfiguration implements ProviderConfiguration{

	@Override
	public AuthProviders providers() {
		// TODO Auto-generated method stub
		return new AuthProviders(JAASProvider.class);
	}



}