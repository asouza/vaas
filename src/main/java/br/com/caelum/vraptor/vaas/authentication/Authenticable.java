package br.com.caelum.vraptor.vaas.authentication;

import java.security.Principal;
import java.util.Collection;

import br.com.caelum.vraptor.vaas.authorization.Role;

public interface Authenticable extends Principal{

	public Collection<? extends Role> getRoles();
}
