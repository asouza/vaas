package br.com.caelum.vraptor.security;

import br.com.caelum.vraptor.vaas.ProviderConfiguration;
import br.com.caelum.vraptor.vaas.authentication.AuthProviders;

public class VaasConfiguration implements ProviderConfiguration{

	@Override
	public AuthProviders providers() {
		return new AuthProviders(CustomDBProvider.class);
	}

}