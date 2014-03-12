package br.com.caelum.vraptor.vaas;

import javax.servlet.http.HttpServletRequest;

public class SecuredUrl {

	private String url;
	private HttpMethod httpMethod;

	public SecuredUrl(String url, HttpMethod httpMethod) {
		super();
		this.url = url;
		this.httpMethod = httpMethod;
	}

	public SecuredUrl(String url) {
		super();
		this.url = url;
	}
	
	public boolean accepts(HttpServletRequest request){
		if(httpMethod==null) {
			return true;
		}
		return HttpMethod.of(request).equals(httpMethod);
	}
	
	public String getUrl() {
		return url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((httpMethod == null) ? 0 : httpMethod.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SecuredUrl other = (SecuredUrl) obj;
		if (httpMethod != other.httpMethod)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
	

}
