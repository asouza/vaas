package br.com.caelum.vraptor.security;

import static br.com.caelum.vraptor.vaas.configurations.JAASRule.withRoles;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.vaas.RulesByURL;
import br.com.caelum.vraptor.vaas.configurations.JAASRule;
import br.com.caelum.vraptor.vaas.configurations.LoggedRule;
import br.com.caelum.vraptor.vaas.configurations.RulesConfiguration;

@ApplicationScoped
public class JAASSecurityRoutes implements RulesConfiguration {

	@Inject
	private LoggedRule loggedRule;
	
	public RulesByURL rulesByURL() {
		RulesByURL rulesByURL = new RulesByURL();
		rulesByURL.defaultRule(loggedRule)
		.add("/main", withRoles("ROLE_USER","ROLE_ADMIN"))
		.add("/user-page", withRoles("ROLE_USER","ROLE_ADMIN"))
		.add("/admin-page", withRoles("ROLE_USER","ROLE_ADMIN"));
		return rulesByURL;
	}

}
