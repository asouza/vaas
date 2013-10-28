package br.com.caelum.vraptor.vaas;

import java.util.List;

import javax.enterprise.inject.Vetoed;

import br.com.caelum.vraptor.vaas.configurations.RulesConfiguration;

@Vetoed
public class RolesConfigMethod {

	private RulesConfiguration accessConfiguration;

	public RolesConfigMethod(RulesConfiguration accessConfiguration) {
		this.accessConfiguration = accessConfiguration;
	}

	@SuppressWarnings("unchecked")
	public List<Rule> rulesFor(String uri) {
		return (List<Rule>) accessConfiguration.getRules(uri);
	}

}
