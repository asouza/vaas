package br.com.caelum.vraptor.vaas;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;

@ApplicationScoped
public class ConfigurationFinder {

	@SuppressWarnings({ "rawtypes", "unchecked"})
	public <T> Instance<T> findOne(AnnotationLiteral<?> confAnnotation) {
		Instance possibleConfigurations = CDI.current().select(confAnnotation);
		isAmbiguos(possibleConfigurations);
		return possibleConfigurations;
	}

	private void isAmbiguos(@SuppressWarnings("rawtypes") Instance possibleConfigurations) {
		if (possibleConfigurations.isAmbiguous()) {
			throw new RuntimeException(
					"You should have just one of "+possibleConfigurations+" class");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> Instance<T> findOne(Class<? extends T> implementationClass, AnnotationLiteral<?>... qualifiers) {
		Instance possibleConfigurations = CDI.current().select(implementationClass, qualifiers);
		return possibleConfigurations;
	}
}
