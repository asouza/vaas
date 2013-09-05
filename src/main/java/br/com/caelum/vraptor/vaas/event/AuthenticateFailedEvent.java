package br.com.caelum.vraptor.vaas.event;

import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletException;

@RequestScoped
public class AuthenticateFailedEvent {

	private ServletException exception;

	public AuthenticateFailedEvent(ServletException exception) {
		this.exception = exception;
	}
	
	public String getContainerReason() {
		return exception.getMessage();
	}

}
