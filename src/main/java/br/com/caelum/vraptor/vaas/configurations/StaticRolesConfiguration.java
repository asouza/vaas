package br.com.caelum.vraptor.vaas.configurations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.vaas.AccessConfiguration;
import br.com.caelum.vraptor.vaas.RolesConfiguration;
import br.com.caelum.vraptor.vaas.Rule;
import br.com.caelum.vraptor.vaas.configurations.LoggedRule;
import br.com.caelum.vraptor.vaas.configurations.RolesMapping;

@ApplicationScoped
@AccessConfiguration
public class StaticRolesConfiguration {
	
	private final Map<String,List<? extends Rule>> uriRules = new HashMap<String, List<? extends Rule>>();
	
	@Inject
	private LoggedRule loggedRule;
	@Inject
	private HttpServletRequest request;
	@Inject
	private RolesMapping mappings;
	
	@PostConstruct
	public void configure(){
		Map<String, List<String>> rolesByUri = mappings.getSimpleRules();
		Set<Entry<String, List<String>>> mappings = rolesByUri.entrySet();
		for (Entry<String, List<String>> entry : mappings) {
			List<String> roles = entry.getValue();
			uriRules.put(entry.getKey(), rulesFor(roles));
		}
	}

	private List<Rule> rulesFor(List<String> roles) {
		List<Rule> rules = new ArrayList<Rule>();
		for (String role : roles) {
			rules.add(new SimpleRoleRule(request, role));
		}
		rules.add(loggedRule);
		return rules;
	}

	@RolesConfiguration
	public List<? extends Rule> rules(String uri) {
		return uriRules.get(uri);
	}

}
