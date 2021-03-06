package br.com.caelum.vraptor.vaas.configurations;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.vaas.Rule;
import br.com.caelum.vraptor.vaas.SecuredUrl;

/**
 * This class provides a easy way for create association between URL and
 * {@link Rule}.
 * 
 * @author Alberto Souza, Francisco Sokol, Mario Amaral
 * 
 */
@ApplicationScoped
public class DefaultURLAccess implements URLAccess{

	private RulesConfiguration rulesConfiguration;

	@Inject
	public DefaultURLAccess(RulesConfiguration rulesConfiguration) {
		this.rulesConfiguration = rulesConfiguration;
	}

	@Deprecated
	public DefaultURLAccess() {
	}

	public Set<Rule> getRules(SecuredUrl url) {
		Set<Rule> rules = rulesConfiguration.rulesByURL().rulesFor(url);
		if (rules == null) {
			String regexKey = tryToFindKeyBasedOnRegex(url);
			rules = rulesConfiguration.rulesByURL().rulesFor(regexKey);
		}
		return firstNonNull(rules, new HashSet<Rule>());
	}

	private <T> T firstNonNull(T first, T other) {
		return first != null ? first : other;
	}

	private String tryToFindKeyBasedOnRegex(SecuredUrl url) {
		Set<SecuredUrl> keys = rulesConfiguration.rulesByURL().getURLs();
		for (SecuredUrl key : keys) {
			if (url.getUrl().matches(key.getUrl())) {
				return key.getUrl();
			}
		}
		return null;
	}

}
