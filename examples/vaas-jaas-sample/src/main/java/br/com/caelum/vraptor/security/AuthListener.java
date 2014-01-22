package br.com.caelum.vraptor.security;

import java.security.Principal;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.AppController;
import br.com.caelum.vraptor.controller.AuthController;
import br.com.caelum.vraptor.vaas.event.AuthenticateFailedEvent;
import br.com.caelum.vraptor.vaas.event.AuthenticatedEvent;
import br.com.caelum.vraptor.vaas.event.AuthorizationFailedEvent;
import br.com.caelum.vraptor.vaas.event.LogoutEvent;

public class AuthListener {
	
	@Inject
	private Result result;
	
	public void login(@Observes AuthenticatedEvent event){
		Principal userPrincipal = event.getUserPrincipal();
		System.out.println("logged as " + userPrincipal);
		result.redirectTo(AppController.class).main();
	}
	
	public void loginFailed(@Observes AuthenticateFailedEvent event){
		System.out.println("Authentication failed, redirect to loginPage...");
		if(event.hasErrors()){
			String message = event.getExceptions().get(0).getMessage(); // just the first message
			result.include("message",message);
		}
		result.redirectTo(AuthController.class).home();
	}
	
	public void logout(@Observes LogoutEvent event) throws ServletException{
		request.logout();
		result.redirectTo(AuthController.class).home();
	}
	
	public void unauthorized(@Observes AuthorizationFailedEvent ev){
		System.out.println("Not Allowed by: " + ev.getRolesNotAllowed());
		result.redirectTo(AuthController.class).unauthorized();
	}
	
	

}
