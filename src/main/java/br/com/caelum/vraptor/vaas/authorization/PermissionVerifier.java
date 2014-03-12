package br.com.caelum.vraptor.vaas.authorization;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.vaas.ConfigurationFinder;
import br.com.caelum.vraptor.vaas.HttpMethod;
import br.com.caelum.vraptor.vaas.Rule;
import br.com.caelum.vraptor.vaas.SecuredUrl;
import br.com.caelum.vraptor.vaas.configurations.URLAccess;


@RequestScoped
public class PermissionVerifier {
	
	private static final Logger logger = LoggerFactory.getLogger(PermissionVerifier.class);
	

	@Inject
	private ConfigurationFinder configurationFinder;
	
	@Inject
	private HttpServletRequest request;


	private URLAccess accessConfiguration;

	
	@PostConstruct
	public void config() {
		accessConfiguration = configurationFinder.findOne(URLAccess.class).get();
	}
	
	public List<Rule> verifyAccessFor(String uri){
		logger.debug("Verifing access for {}", uri);
		
		Set<Rule> rulesForUri = accessConfiguration.getRules(new SecuredUrl(uri,HttpMethod.of(request)));
		logger.debug("Rules found: {}",rulesForUri);
		
		List<Rule> rulesNotAllowed = getNotAllowedRules(rulesForUri);
		return rulesNotAllowed;

	}

	private List<Rule> getNotAllowedRules(Set<Rule> rulesForUri) {
		List<Rule> rulesNotAllowed = new ArrayList<Rule>();

		for (Rule rule : rulesForUri) {
			if(!rule.isAuthorized(request)){
				rulesNotAllowed.add(rule);
			}
		}
		return rulesNotAllowed;
	}

	
}
