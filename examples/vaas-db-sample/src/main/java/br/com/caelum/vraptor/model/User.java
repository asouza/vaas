package br.com.caelum.vraptor.model;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class User implements Principal{

	@Id
	@GeneratedValue
	private Long id;
	private String login;
	private String userName;
	private String password;

	@ManyToMany
	private Set<Role> roles = new HashSet<Role>();
	
	@Override
	public String getName() {
		return login;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", userName=" + userName + ", password=" + password + ", roles=" + roles	+ "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
