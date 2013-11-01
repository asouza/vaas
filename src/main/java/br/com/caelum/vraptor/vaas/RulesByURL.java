package br.com.caelum.vraptor.vaas;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;


public class RulesByURL {
	
	private Map<String,Set<Rule>> allRules = new HashMap<String, Set<Rule>>();
	private Set<Rule> defaultRules = new LinkedHashSet<Rule>();

	public RulesByURL add(String url, Rule... rules) {		
		Set<Rule> specificRules = Sets.newLinkedHashSet(defaultRules);
		for (Rule rule : rules) {
			specificRules.add(rule);
		}
		allRules.put(url,specificRules);
		return this;
	}

	public Set<String> getURLs() {
		return allRules.keySet();
	}

	public Set<Rule> rulesFor(String url) {
		return allRules.get(url);
	}

	public RulesByURL defaultRule(Rule rule) {
		defaultRules.add(rule);
		return this;
	}


}
