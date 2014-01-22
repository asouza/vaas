package br.com.caelum.vraptor.vaas;

import javax.servlet.http.HttpServletRequest;

public interface Rule {
	//TODO return a qualifier that indicates how to handle the event
	boolean isAuthorized(HttpServletRequest request);
}
