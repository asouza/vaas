package br.com.caelum.vraptor.vaas;

import br.com.caelum.vraptor.vaas.authentication.AuthProvider;


/**
 * Interface that should be implemented for configure {@link AuthProvider}
 * @author Alberto Souza
 *
 */
public interface ProviderConfiguration {
	public AuthProviders providers();
}
