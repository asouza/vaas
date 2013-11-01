package br.com.caelum.vraptor.vaas.configuration;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.caelum.vraptor.vaas.Rule;
import br.com.caelum.vraptor.vaas.RulesByURL;
import br.com.caelum.vraptor.vaas.configurations.DefaultURLAccess;
import br.com.caelum.vraptor.vaas.configurations.JAASRolesRule;
import br.com.caelum.vraptor.vaas.configurations.LoggedRule;
import br.com.caelum.vraptor.vaas.configurations.RulesConfiguration;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class StaticRulesConfigurationTest {

	@Mock
	private LoggedRule loggedRule;
	@Mock
	private HttpServletRequest request;
	private DefaultURLAccess urlAccess;
	
	{
		final RulesByURL rulesByURL = new RulesByURL();
		rulesByURL.add("/",new JAASRolesRule(request,"admin")).
		add("/users/.*",new JAASRolesRule(request,"comercial")).
		add("/users/.*/task/.*",new JAASRolesRule(request,"employee"));
		RulesConfiguration rulesConfiguration = new RulesConfiguration() {
			
			@Override
			public RulesByURL rulesByURL() {
				return rulesByURL;
			}
		};
		this.urlAccess = new DefaultURLAccess(rulesConfiguration);
		
	}
	
	@Test
	public void shouldFindRulesForSimpleURL() throws Exception {
		Set<Rule> rules = urlAccess.getRules("/");
		assertTrue(rules.contains(new JAASRolesRule(request,"admin")));
	}
	
	
	@Test
	public void shouldFindRulesForRegexedURL() throws Exception {
		Set<Rule> rules = urlAccess.getRules("/users/1");
		assertTrue(rules.contains(new JAASRolesRule(request,"comercial")));
	}
	
	@Test
	public void shouldFindRulesForRegexedURL2() throws Exception {
		Set<Rule> rules = urlAccess.getRules("/users/1/task/3");
		assertTrue(rules.contains(new JAASRolesRule(request,"employee")));
	}
	
}
