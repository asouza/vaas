package br.com.caelum.vraptor.vaas.matchers;

import net.vidageek.mirror.dsl.Mirror;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class VaasMatchers<T> {
	public Matcher<T> hasAttributeValue(final String attributeName, final String value) {
		return new BaseMatcher<T>() {
			private Object fieldValue;

			@Override
			public boolean matches(Object flow) {
				fieldValue = new Mirror().on(flow).get().field(attributeName);
				return fieldValue.equals(value);
			}

			@Override
			public void describeTo(Description desc) {
				desc
					.appendText("Expecting ")
					.appendValue(attributeName)
					.appendText("to be the value of the attribute ")
					.appendValue(attributeName)
					.appendText("but was ")
					.appendValue(fieldValue);
			}
		
		};
	}

}
