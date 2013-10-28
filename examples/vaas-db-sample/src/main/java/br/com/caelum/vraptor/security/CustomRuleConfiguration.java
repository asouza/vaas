package br.com.caelum.vraptor.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.model.Role;
import br.com.caelum.vraptor.model.URLAccessRole;
import br.com.caelum.vraptor.model.User;
import br.com.caelum.vraptor.vaas.AccessConfiguration;
import br.com.caelum.vraptor.vaas.Rule;
import br.com.caelum.vraptor.vaas.authentication.VaasSession;
import br.com.caelum.vraptor.vaas.configurations.LoggedRule;
import br.com.caelum.vraptor.vaas.configurations.RulesConfiguration;

@ApplicationScoped
@AccessConfiguration
@Alternative @Priority(500)
public class CustomRuleConfiguration implements RulesConfiguration {
	
	@Inject
	private EntityManager em;
	
	@Inject
	private LoggedRule loggedRule;
	
	@Inject
	private VaasSession userSession;
	
	private Map<String, Set<Role>> rolesForUrl = new HashMap<String, Set<Role>>(); 
	
	@PostConstruct
	public void init(){
		List<URLAccessRole> accessRoles = em.createQuery("from URLAccessRole",URLAccessRole.class).getResultList();
		for (URLAccessRole urlAccessRole : accessRoles) {
			rolesForUrl.put(urlAccessRole.getUrl(), urlAccessRole.getAllowedRoles());
		}
	}
	
	
	@Override
	public List<Rule> getRules(String uri) {
		Set<Role> rolesAllowed = rolesForUrl.get(uri);
		
		if(rolesAllowed == null){
			return Collections.emptyList();
		}
		
		return Arrays.asList(loggedRule, new SimpleRoleBasedRule(userSession, rolesAllowed));
	}

}
