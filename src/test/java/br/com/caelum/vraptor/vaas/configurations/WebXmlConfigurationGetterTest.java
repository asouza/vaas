package br.com.caelum.vraptor.vaas.configurations;

import static junit.framework.Assert.assertEquals;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WebXmlConfigurationGetterTest {
	private static final String INEXISTENT_KEY = "inexistentKey";
	private static final String DEFAULT = "default";
	@Mock private ServletContext context;
	private WebXmlConfigurationGetter config;

	@Before
	public void setUp() {
		config = new WebXmlConfigurationGetter(context);
	}
	
	@Test
	public void shouldGetDefaultValue() {
		WebXmlConfigurationGetter config = new WebXmlConfigurationGetter(context);
		String value = config.getOrDefault(DEFAULT, INEXISTENT_KEY);
		assertEquals(DEFAULT, value);
	}
	
	@Test
	public void shouldGetConfiguredValue() {
		String customValue = "my value";
		Mockito.when(context.getInitParameter(INEXISTENT_KEY)).thenReturn(customValue);
		String value = config.getOrDefault(DEFAULT, INEXISTENT_KEY);
		assertEquals(customValue, value);
	}

}
