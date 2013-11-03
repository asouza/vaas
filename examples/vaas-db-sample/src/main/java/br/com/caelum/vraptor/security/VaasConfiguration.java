package br.com.caelum.vraptor.security;

import br.com.caelum.vraptor.vaas.AuthProviders;
import br.com.caelum.vraptor.vaas.ProviderConfiguration;

public class VaasConfiguration implements ProviderConfiguration{

	@Override
	public AuthProviders providers() {
		return new AuthProviders(CustomDBProvider.class);
	}

}