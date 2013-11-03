package br.com.caelum.vraptor.vaas;

import br.com.caelum.vraptor.vaas.authentication.AuthProvider;
import br.com.caelum.vraptor.vaas.authentication.AuthProviders;


/**
 * Interface that should be implemented for configure {@link AuthProvider}
 * @author Alberto Souza
 *
 */
public interface ProviderConfiguration {
	public AuthProviders providers();
}
