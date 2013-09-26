package br.com.caelum.vraptor.vaas;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;

@Intercepts
@ApplicationScoped
public class VaasInterceptor implements Interceptor {
	
	@Inject
	private AuthenticationAuthorizationFlow flow;

	@Override
	public void intercept(final InterceptorStack stack, final ControllerMethod method,
			final Object controllerInstance) throws InterceptionException {

		flow.intercept(new Runnable() {			
			@Override
			public void run() {
				stack.next(method, controllerInstance);
			}
		});
	}

	@Override
	public boolean accepts(ControllerMethod method) {
		return true;
	}

}
