package br.com.caelum.vraptor.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Patch;
import br.com.caelum.vraptor.Path;

@Controller
public class AuthController {
	
	@Path("/")
	public void home(){
	}
	
	@Path("/login")
	public void login(){
	}
	
	@Path("/logout")
	public void logout(){
	}

	@Path("/denied")
	public void unauthorized() {
	}

}
