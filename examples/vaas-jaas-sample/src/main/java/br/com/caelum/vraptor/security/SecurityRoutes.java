package br.com.caelum.vraptor.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.vraptor.vaas.configurations.RolesMapping;

public class SecurityRoutes implements RolesMapping {

	private static Map<String, List<String>> routes = new HashMap<String, List<String>>();
	
	static{
		routes.put("/main", Arrays.asList("ROLE_USER","ROLE_ADMIN"));
		routes.put("/user-page", Arrays.asList("ROLE_USER","ROLE_ADMIN"));
		routes.put("/admin-page", Arrays.asList("ROLE_ADMIN"));
	}
	
	@Override
	public Map<String, List<String>> getSimpleRules() {
		return routes;
	}

}
