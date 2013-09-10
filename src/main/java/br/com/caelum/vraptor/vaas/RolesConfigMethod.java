package br.com.caelum.vraptor.vaas;

import java.lang.reflect.Method;
import java.util.List;

import javax.enterprise.inject.Vetoed;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.Matcher;

@Vetoed
public class RolesConfigMethod {

	private Object accessConfiguration;
	private Method rolesConfig;

	public RolesConfigMethod(Object accessConfiguration) {
		this.accessConfiguration = accessConfiguration;
		rolesConfig = new Mirror().on(accessConfiguration.getClass())
				.reflectAll().methods().matching(new RolesMatcher()).get(0);		
	}

	@SuppressWarnings("unchecked")
	public List<Rule> rolesFor(String uri) {
		return (List<Rule>) new Mirror()
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
