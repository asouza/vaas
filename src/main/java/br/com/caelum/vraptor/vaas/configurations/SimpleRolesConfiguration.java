package br.com.caelum.vraptor.vaas.configurations;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.vaas.AccessConfiguration;
import br.com.caelum.vraptor.vaas.Rule;

@AccessConfiguration
public class SimpleRolesConfiguration {
	
	@Inject
	private SimpleRoleRule rule;
	
	public List<Rule> getRules(String uri) {
		return Arrays.<Rule>asList(rule);
	}

}
