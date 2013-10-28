package br.com.caelum.vraptor.vaas.configurations;

import java.util.List;

import br.com.caelum.vraptor.vaas.Rule;

public interface RulesConfiguration {

	public abstract List<Rule> getRules(String uri);

}