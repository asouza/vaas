package br.com.caelum.vraptor.vaas.configurations;

import java.util.Set;

import br.com.caelum.vraptor.vaas.Rule;
import br.com.caelum.vraptor.vaas.SecuredUrl;

public interface URLAccess {
	public Set<Rule> getRules(SecuredUrl url);
}
