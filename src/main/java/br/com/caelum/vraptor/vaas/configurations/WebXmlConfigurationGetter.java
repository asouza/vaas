package br.com.caelum.vraptor.vaas.configurations;

import javax.inject.Inject;
import javax.servlet.ServletContext;

public class WebXmlConfigurationGetter {

	private final ServletContext context;

	@Inject
	public WebXmlConfigurationGetter(ServletContext context) {
		this.context = context;
	}
	
	public String getOrDefault(String defaultValue, String webXmlKey) {
		String configuredValue = context.getInitParameter(webXmlKey);
		if(configuredValue != null && !configuredValue.isEmpty())
			return configuredValue;
		return defaultValue;
	}
}
