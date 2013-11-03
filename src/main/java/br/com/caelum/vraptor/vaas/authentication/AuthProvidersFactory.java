package br.com.caelum.vraptor.vaas.authentication;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import br.com.caelum.vraptor.vaas.ConfigurationFinder;
import br.com.caelum.vraptor.vaas.InstanceProviderList;
import br.com.caelum.vraptor.vaas.ProviderConfiguration;

@ApplicationScoped
public class AuthProvidersFactory {
	@Inject private ProviderConfiguration providerConfiguration;
	@Inject private ConfigurationFinder configurationFinder;
	@Produces @InstanceProviderList private List<Instance<AuthProvider>> instancesOfProviders = new ArrayList<Instance<AuthProvider>>();
	
	@PostConstruct
	public void configure(){
		List<Class<? extends AuthProvider>> providersClasses = providerConfiguration.providers().get();
		for (Class<? extends AuthProvider> providerClass : providersClasses) {
			Instance<AuthProvider> provider = configurationFinder.findOne(providerClass);
			instancesOfProviders .add(provider);
		}
	}
	
	
	
}
