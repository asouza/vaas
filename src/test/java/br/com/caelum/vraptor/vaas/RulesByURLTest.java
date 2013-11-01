package br.com.caelum.vraptor.vaas;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

import static org.junit.Assert.*;

public class RulesByURLTest {
	
	private static class Rule1 implements Rule{

		@Override
		public boolean isAuthorized() {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	private static class Rule2 implements Rule{
		
		@Override
		public boolean isAuthorized() {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	private RulesByURL rulesByURL;
	private Rule rule1 = new Rule1();
	private Rule rule2 = new Rule1();
	
	@Before
	public void setup() {
		rulesByURL = new RulesByURL();
	}

	@Test
	public void shouldAddARuleForURI() throws Exception {		
		RulesByURL config = rulesByURL.add("/main",rule1,rule2).add("/bla",rule1);
		
		assertEquals(Sets.newHashSet("/main","/bla"), config.getURLs());
		assertEquals(Sets.newHashSet(rule1,rule2), config.rulesFor("/main"));
		assertEquals(Sets.newHashSet(rule1), config.rulesFor("/bla"));
	}
	
	@Test
	public void shouldAddDefaultRuleForAllURLs() throws Exception {		
		RulesByURL config = rulesByURL.defaultRule(rule1).add("/main",rule2).add("/bla");
		
		assertEquals(Sets.newHashSet("/main","/bla"), config.getURLs());
		assertEquals(Sets.newHashSet(rule1,rule2), config.rulesFor("/main"));
		assertEquals(Sets.newHashSet(rule1), config.rulesFor("/bla"));
	}
		
}
