package br.com.caelum.vraptor.vaas.configurations;

import java.util.ArrayList;
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

@ApplicationScoped
@AccessConfiguration
public class StaticRolesConfiguration implements RulesConfiguration {

	private final Map<String, List<Rule>> uriRules = new HashMap<String, List<Rule>>();

	private LoggedRule loggedRule;
	private HttpServletRequest request;
	private RolesMapping mappings;
	
	@Deprecated
	public StaticRolesConfiguration(){}

	@Inject
	public StaticRolesConfiguration(LoggedRule loggedRule,
			HttpServletRequest request, RolesMapping mappings) {
		this.loggedRule = loggedRule;
		this.request = request;
		this.mappings = mappings;
	}

	@PostConstruct
	public void configure() {
		Map<String, List<String>> rolesByUri = mappings.getSimpleRules();
		Set<Entry<String, List<String>>> mappings = rolesByUri.entrySet();
		for (Entry<String, List<String>> entry : mappings) {
			List<String> roles = entry.getValue();
			uriRules.put(entry.getKey(), rulesFor(roles));
		}
	}

	private List<Rule> rulesFor(List<String> roles) {
		List<Rule> rules = new ArrayList<Rule>();
		rules.add(loggedRule);
		rules.add(new JAASRolesRule(request, roles.toArray(new String[] {})));
		return rules;
	}

	/* (non-Javadoc)
	 * @see br.com.caelum.vraptor.vaas.configurations.RulesConfiguration#rules(java.lang.String)
	 */
	@Override
	@RolesConfiguration
	public List<Rule> getRules(String uri) {
		List<Rule> rules = uriRules.get(uri);
		if (rules==null) {
			String regexKey = tryToFindKeyBasedOnRegex(uri);
			rules = uriRules.get(regexKey);
		}
		return rules;
	}

	private String tryToFindKeyBasedOnRegex(String uri) {
		Set<String> keys = uriRules.keySet();
		for (String key : keys) {
			if (uri.matches(key)) {
				return key;
			}
		}
		return null;
	}

}
