package br.com.caelum.vraptor.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;

@Controller
public class AppController {
	
	@Path("/main")
	public void main(){
		System.out.println("accessing main");
	}
	
	@Path("/user-page")
	public void userPage(){
		System.out.println("accessing user page");
	}
	
	@Path("/admin-page")
	public void adminPage(){
		System.out.println("accessing admin page");
	}
}
