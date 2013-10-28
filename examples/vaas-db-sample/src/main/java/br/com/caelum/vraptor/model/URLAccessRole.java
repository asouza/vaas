package br.com.caelum.vraptor.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class URLAccessRole {

	@Id
	@GeneratedValue
	private Long id;

	private String url;

	@ManyToMany
	private Set<Role> allowedRoles = new HashSet<Role>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<Role> getAllowedRoles() {
		return allowedRoles;
	}

	public void setAllowedRoles(Set<Role> allowedRoles) {
		this.allowedRoles = allowedRoles;
	}

}
