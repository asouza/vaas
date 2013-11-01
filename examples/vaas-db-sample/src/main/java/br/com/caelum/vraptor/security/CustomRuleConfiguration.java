package br.com.caelum.vraptor.security;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.model.URLAccessRole;
import br.com.caelum.vraptor.vaas.RulesByURL;
import br.com.caelum.vraptor.vaas.authentication.VaasSession;
import br.com.caelum.vraptor.vaas.configurations.LoggedRule;
import br.com.caelum.vraptor.vaas.configurations.RulesConfiguration;

@ApplicationScoped
public class CustomRuleConfiguration implements RulesConfiguration {
	
	@Inject
	private EntityManager em;
	
	@Inject
	private LoggedRule loggedRule;
	
	@Inject
	private VaasSession userSession;
	
	private RulesByURL rulesByURL = new RulesByURL(); 
	
	@PostConstruct
	public void init(){
		rulesByURL.defaultRule(loggedRule);
		List<URLAccessRole> accessRoles = em.createQuery("from URLAccessRole",URLAccessRole.class).getResultList();
		for (URLAccessRole urlAccessRole : accessRoles) {			
			rulesByURL.add(urlAccessRole.getUrl(), new SimpleRoleBasedRule(userSession,urlAccessRole.getAllowedRoles()));
		}
	}
	
	
	public RulesByURL rulesByURL() {
		return rulesByURL;
	}

}
