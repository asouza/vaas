package br.com.caelum.vraptor.vaas;

public interface Rule {
	//TODO return a qualifier that indicates how to handle the event
	boolean isAuthorized();
}
