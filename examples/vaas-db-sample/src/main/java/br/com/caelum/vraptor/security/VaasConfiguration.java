package br.com.caelum.vraptor.security;

import br.com.caelum.vraptor.vaas.ProviderConfiguration;
import br.com.caelum.vraptor.vaas.authentication.AuthProvider;
import br.com.caelum.vraptor.vaas.authentication.JAASProvider;

public class VaasConfiguration implements ProviderConfiguration{

	@Override
	public Class<? extends AuthProvider>[] providers() {
		return new Class[]{CustomDBProvider.class};
	}

}