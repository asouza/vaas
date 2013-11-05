package br.com.caelum.vraptor.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import br.com.caelum.vraptor.vaas.authorization.Group;

@Entity
public class URLAccessRole {

	@Id
	@GeneratedValue
	private Long id;

	private String url;

	@ManyToMany(fetch=FetchType.EAGER,targetEntity=Role.class)
	private Set<Group> allowedRoles = new HashSet<Group>();

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

	public Collection<Group> getAllowedRoles() {
		return allowedRoles;
	}

}
