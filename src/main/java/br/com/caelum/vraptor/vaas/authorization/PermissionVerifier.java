package br.com.caelum.vraptor.vaas.authorization;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.vaas.AccessConfiguration;
import br.com.caelum.vraptor.vaas.ConfigurationFinder;
import br.com.caelum.vraptor.vaas.RolesConfigMethod;
import br.com.caelum.vraptor.vaas.Rule;
import br.com.caelum.vraptor.vaas.configurations.RulesConfiguration;


@RequestScoped
public class PermissionVerifier {
	
	private static final Logger logger = LoggerFactory.getLogger(PermissionVerifier.class);
	
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
		logger.debug("Verifing access for {}", uri);
		
		List<Rule> rulesForUri = rulesConfigMethod.rulesFor(uri);
		logger.debug("Rules found: {}",rulesForUri);
		
		List<Rule> rulesNotAllowed = getNotAllowedRules(rulesForUri);
		return rulesNotAllowed;

	}

	private List<Rule> getNotAllowedRules(List<Rule> roles) {
		List<Rule> rulesNotAllowed = new ArrayList<Rule>();

		for (Rule rule : roles) {
			if(!rule.isAuthorized()){
				rulesNotAllowed.add(rule);
			}
		}
		return rulesNotAllowed;
	}

	
}
