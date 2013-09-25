package br.com.caelum.vraptor.vaas.authorization;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import br.com.caelum.vraptor.vaas.AccessConfiguration;
import br.com.caelum.vraptor.vaas.ConfigurationFinder;
import br.com.caelum.vraptor.vaas.RolesConfigMethod;
import br.com.caelum.vraptor.vaas.Rule;
import br.com.caelum.vraptor.vaas.event.AuthorizationFailedEvent;
import br.com.caelum.vraptor.vaas.event.RefreshUserEvent;


@RequestScoped
public class PermissionVerifier {
	@Inject
	private Event<AuthorizationFailedEvent> authorizationFailedEvent;

	@Inject
	private Event<RefreshUserEvent> refreshUserEvent;

	private RolesConfigMethod rulesConfigMethod;

	private Object accessConfiguration;
	
	@Inject
	private ConfigurationFinder configurationFinder;

	
	@SuppressWarnings("serial")
	@PostConstruct
	public void config() {
		this.accessConfiguration = configurationFinder.find(new AnnotationLiteral<AccessConfiguration>() {});
		this.rulesConfigMethod = new RolesConfigMethod(this.accessConfiguration);
	}

	
	public boolean verifyAccessFor(String uri){
		List<Rule> rulesNotAllowed = getNotAllowedRules(rulesConfigMethod.rulesFor(uri));
		if (!rulesNotAllowed.isEmpty()) {
			authorizationFailedEvent.fire(new AuthorizationFailedEvent(rulesNotAllowed));
			return false;
		}
		refreshUserEvent.fire(new RefreshUserEvent());
		return true;

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
