package br.com.caelum.vraptor.vaas;

import java.lang.reflect.Method;
import java.util.List;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.Matcher;

public class RolesConfigMethod {

	private Object accessConfiguration;
	private Method rolesConfig;

	public RolesConfigMethod(Object accessConfiguration) {
		super();
		this.accessConfiguration = accessConfiguration;
		rolesConfig = new Mirror().on(accessConfiguration.getClass())
				.reflectAll().methods().matching(new RolesMatcher()).get(0);		
	}

	@SuppressWarnings("unchecked")
	public List<String> rolesFor(String uri) {
		return (List<String>) new Mirror()
				.on(accessConfiguration).invoke().method(rolesConfig)
				.withArgs(uri);
	}

	private static class RolesMatcher implements Matcher<Method> {

		@Override
		public boolean accepts(Method element) {
			return element.getAnnotation(RolesConfiguration.class) != null;
		}

	}

}
