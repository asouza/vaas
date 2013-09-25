package br.com.caelum.vraptor.vaas.event;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AuthenticateFailedEvent {

	private Exception exception;

	public AuthenticateFailedEvent(Exception exception) {
		this.exception = exception;
	}
	
	public String getContainerReason() {
		return exception.getMessage();
	}

}
