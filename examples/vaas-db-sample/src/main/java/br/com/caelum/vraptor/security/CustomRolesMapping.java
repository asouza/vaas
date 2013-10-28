package br.com.caelum.vraptor.security;

import java.util.List;
import java.util.Map;

import br.com.caelum.vraptor.vaas.configurations.RolesMapping;

public class CustomRolesMapping implements RolesMapping{

	@Override
	public Map<String, List<String>> getSimpleRules() {
		return null;
	}

}
