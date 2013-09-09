package br.com.caelum.vraptor.vaas;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class RolesConfigMethodTest {
	private static final SomeRule RULE = new SomeRule();

	@Test
	public void shouldGetRolesFromConfiguration() {
		RolesConfigMethod method = new RolesConfigMethod(new SomeConfiguration());
		List<Rule> rules = method.rolesFor("/uri");
		Assert.assertEquals(RULE, rules.get(0));
	}
	
	static class SomeConfiguration {

		@RolesConfiguration
		public List<Rule> getRoles(String uri) {
			return Arrays.<Rule>asList(RULE);
		}
		
	}
	
	static class SomeRule implements Rule {
		@Override
		public boolean isAuthorized() {
			return true;
		}
	}

}
