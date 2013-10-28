package br.com.caelum.vraptor.security;

import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.model.User;
import br.com.caelum.vraptor.vaas.authentication.AuthProvider;

/**
 * Searchs for users in my database
 * 
 * @author Mario Amaral
 *
 */
@RequestScoped
public class CustomDBProvider implements AuthProvider{
	
	@Inject
	private EntityManager em;

	@Override
	public Principal authenticate(String login, String password) throws Exception {
		
		try{
			return em.createQuery("from User u where u.login = :login and u.password = password",User.class)
					.setParameter("login", login)
					.setParameter("password", password)
					.getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

}
