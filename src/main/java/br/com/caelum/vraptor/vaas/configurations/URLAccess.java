package br.com.caelum.vraptor.vaas.configurations;

import java.util.Set;

import br.com.caelum.vraptor.vaas.Rule;

public interface URLAccess {
	public Set<Rule> getRules(String url);
}
