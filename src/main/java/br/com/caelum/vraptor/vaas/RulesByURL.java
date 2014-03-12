package br.com.caelum.vraptor.vaas;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

public class RulesByURL {

	private Map<SecuredUrl, Set<Rule>> allRules = new HashMap<SecuredUrl, Set<Rule>>();
	private Set<Rule> defaultRules = new LinkedHashSet<Rule>();

	public RulesByURL add(String url, Rule... rules) {
		add(url,null,rules);
		return this;
	}

	public Set<SecuredUrl> getURLs() {
		return allRules.keySet();
	}

	public Set<Rule> rulesFor(SecuredUrl url) {
		return allRules.get(url);
	}
	
	public Set<Rule> rulesFor(String url) {
		return rulesFor(url,null);
	}
	
	public Set<Rule> rulesFor(String url,HttpMethod httpMethod) {
		return allRules.get(new SecuredUrl(url,httpMethod));
	}

	public RulesByURL defaultRule(Rule rule) {
		defaultRules.add(rule);
		return this;
	}

	public RulesByURL add(String url, HttpMethod httpMethod, Rule... rules) {
		Set<Rule> specificRules = Sets.newLinkedHashSet(defaultRules);
		for (Rule rule : rules) {
			specificRules.add(rule);
		}
		allRules.put(new SecuredUrl(url, httpMethod), specificRules);
		return this;
	}

}
