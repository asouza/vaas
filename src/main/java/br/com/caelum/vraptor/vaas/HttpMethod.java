package br.com.caelum.vraptor.vaas;

import javax.servlet.http.HttpServletRequest;

public enum HttpMethod {
	GET, POST, PUT, DELETE, TRACE, HEAD, OPTIONS, PATCH;

	public static HttpMethod of(HttpServletRequest request) {
		String methodName = request.getMethod();
		try {
			return valueOf(methodName.toUpperCase());
		} catch (IllegalArgumentException e) {
			// funny, but we need a better explanation, to support sendError
			throw new IllegalArgumentException("HTTP Method not known: " + methodName, e);
		}
	}
}
