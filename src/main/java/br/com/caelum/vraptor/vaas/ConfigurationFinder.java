package br.com.caelum.vraptor.vaas;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;

@ApplicationScoped
public class ConfigurationFinder {

	@SuppressWarnings({ "rawtypes"})
	public Object find(AnnotationLiteral<AccessConfiguration> confAnnotation) {
		Instance possibleConfigurations = CDI.current().select(confAnnotation);
		if (possibleConfigurations.isAmbiguous()) {
			throw new RuntimeException(
					"You should use just one "+confAnnotation+" class");
		}
		return possibleConfigurations.get();
	}	
}
