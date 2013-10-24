package br.com.caelum.vraptor.vaas.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

import static java.util.Arrays.asList;
import br.com.caelum.vraptor.vaas.Rule;
import br.com.caelum.vraptor.vaas.configurations.LoggedRule;
import br.com.caelum.vraptor.vaas.configurations.RolesMapping;
import br.com.caelum.vraptor.vaas.configurations.JAASRolesRule;
import br.com.caelum.vraptor.vaas.configurations.StaticRolesConfiguration;

@RunWith(MockitoJUnitRunner.class)
public class StaticRolesConfigurationTest {

	@Mock
	private LoggedRule loggedRule;
	@Mock
	private HttpServletRequest request;
	private StaticRolesConfiguration configuration = 
			new StaticRolesConfiguration(loggedRule, request, new TestRolesMapping());
	
	{
		configuration.configure();
	}
	
	private static class TestRolesMapping implements RolesMapping{
		
		private Map<String, List<String>> map = new HashMap<String, List<String>>();

		{
			map.put("/", asList("admin"));
			map.put("/users", asList("guest"));
			map.put("/users/.*", asList("comercial"));
			map.put("/users/.*/task/.*", asList("employee"));
		}		

		@Override
		public Map<String, List<String>> getSimpleRules() {
			return map;
		}
		
	}
	
	@Test
	public void shouldFindRulesForSimpleURL() throws Exception {
		List<? extends Rule> rules = configuration.rules("/");
		assertTrue(rules.contains(new JAASRolesRule(request,"admin")));
	}
	
	
	@Test
	public void shouldFindRulesForRegexedURL() throws Exception {
		List<? extends Rule> rules = configuration.rules("/users/1");
		assertTrue(rules.contains(new JAASRolesRule(request,"comercial")));
	}
	
	@Test
	public void shouldFindRulesForRegexedURL2() throws Exception {
		List<? extends Rule> rules = configuration.rules("/users/1/task/3");
		assertTrue(rules.contains(new JAASRolesRule(request,"employee")));
	}
	
}
