package br.com.caelum.vraptor.security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.vaas.RulesByURL;
import br.com.caelum.vraptor.vaas.configurations.JAASRolesRule;
import br.com.caelum.vraptor.vaas.configurations.LoggedRule;
import br.com.caelum.vraptor.vaas.configurations.RulesConfiguration;

@ApplicationScoped
public class JAASSecurityRoutes implements RulesConfiguration {

	@Inject
	private LoggedRule loggedRule;
	@Inject
	private HttpServletRequest request;
	
	public RulesByURL rulesByURL() {
		RulesByURL rulesByURL = new RulesByURL();
		rulesByURL.defaultRule(loggedRule)
		.add("/main", new JAASRolesRule(request,"ROLE_USER","ROLE_ADMIN"))
		.add("/user-page", new JAASRolesRule(request,"ROLE_USER","ROLE_ADMIN"))
		.add("/admin-page", new JAASRolesRule(request,"ROLE_USER","ROLE_ADMIN"));
		return rulesByURL;
	}

}
