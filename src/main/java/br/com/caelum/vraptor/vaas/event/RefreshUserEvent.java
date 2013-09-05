package br.com.caelum.vraptor.vaas.event;

import javax.enterprise.context.RequestScoped;

/**
 * This event is intend to be used with systems that use
 * Hibernate or other ORM framework. Maybe you want to 
 * refresh the user to get toMany relations. 
 * @author albertoluizsouza
 *
 */
@RequestScoped
public class RefreshUserEvent {

}
