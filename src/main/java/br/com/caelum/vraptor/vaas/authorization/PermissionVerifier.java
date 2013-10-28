package br.com.caelum.vraptor.vaas.authorization;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import br.com.caelum.vraptor.vaas.AccessConfiguration;
import br.com.caelum.vraptor.vaas.ConfigurationFinder;
import br.com.caelum.vraptor.vaas.RolesConfigMethod;
import br.com.caelum.vraptor.vaas.Rule;
import br.com.caelum.vraptor.vaas.configurations.RulesConfiguration;


@RequestScoped
public class PermissionVerifier {
	private RolesConfigMethod rulesConfigMethod;

	@Inject
	private ConfigurationFinder configurationFinder;

	
	@SuppressWarnings("serial")
	@PostConstruct
	public void config() {
		RulesConfiguration accessConfiguration = configurationFinder.findOne(RulesConfiguration.class,
															new AnnotationLiteral<AccessConfiguration>() {}).get();
		this.rulesConfigMethod = new RolesConfigMethod(accessConfiguration);
	}
	
	public List<Rule> verifyAccessFor(String uri){
		List<Rule> rulesNotAllowed = getNotAllowedRules(rulesConfigMethod.rulesFor(uri));
		return rulesNotAllowed;

	}

	private List<Rule> getNotAllowedRules(List<Rule> roles) {
		List<Rule> rulesNotAllowed = new ArrayList<Rule>();
		if (roles == null) {
			return rulesNotAllowed;
		}
		for (Rule rule : roles) {
			if(!rule.isAuthorized()){
				rulesNotAllowed.add(rule);
			}
		}
		return rulesNotAllowed;
	}

	
}
