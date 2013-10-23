package br.com.caelum.vraptor.vaas.event;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AuthenticateFailedEvent {

	private List<Exception> exceptions = new ArrayList<Exception>();

	public AuthenticateFailedEvent() {
	}
	
	public List<Exception> getExceptions() {
		return exceptions;
	}

	public boolean add(Exception cause) {
		return exceptions.add(cause);
	}
	
	public boolean hasErrors(){
		return !exceptions.isEmpty();
	}
	
}
