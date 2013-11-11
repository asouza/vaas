## VRaptor Authentication and Authorization Service

A simple vraptor plugin to support Authentication and Authorization. Based on CDI events and
programatically configuration.

# installing

vaas can be downloaded from maven's repository, or configured in any compatible tool (check the newest version at http://search.maven.org/):

		<dependency>
			<groupId>br.com.caelum.vraptor</groupId>
			<artifactId>vaas</artifactId>
			<version>1.0.0</version>
			<scope>compile</scope>
		</dependency>
		

#running with JAAS
VAAS has a fine integration with JAAS, the JAVAEE authentication and authorization specification. Let's
take a look. 

First you have to create a Context.xml file in META-INF folder of your web application. Here we are using
a MemoryRealm from Tomcat. You can just add your users and roles in tomcat-users.xml file.

	<Context>
		<Realm className="org.apache.catalina.realm.MemoryRealm" />
	</Context>

Associate URL's and Rules.
	@ApplicationScoped
	public class JAASSecurityRoutes implements RulesConfiguration {

		@Inject
		private LoggedRule loggedRule;
		@Inject
		private HttpServletRequest request;
	
		public RulesByURL rulesByURL() {
			RulesByURL rulesByURL = new RulesByURL();
			rulesByURL.defaultRule(loggedRule)
			.add("/main", new JAASRolesRule(request,"ROLE_USER","ROLE_ADMIN"))
			.add("/user-page", new JAASRolesRule(request,"ROLE_USER","ROLE_ADMIN"))
			.add("/admin-page", new JAASRolesRule(request,"ROLE_USER","ROLE_ADMIN"));
			return rulesByURL;
		}

	}

Configure the desired for providers, in this case, JAASProvider.
	public class VaasConfiguration implements ProviderConfiguration{

		@Override
		public AuthProviders providers() {
		  // TODO Auto-generated method stub
		  return new AuthProviders(JAASProvider.class);
		}
	}

Configure events to handle login, logout and other cases. Here we have a VRaptor application based
example.

	public class AuthListener {
		
		@Inject
		private Result result;
		
		@Inject
		private HttpServletRequest request;
		
		public void login(@Observes AuthenticatedEvent event){
			Principal userPrincipal = event.getUserPrincipal();
			result.redirectTo(AppController.class).main();
		}
		
		public void loginFailed(@Observes AuthenticateFailedEvent event){
			result.redirectTo(AuthController.class).home();
		}
		
		public void logout(@Observes LogoutEvent event) throws ServletException{
			request.logout();
			result.redirectTo(AuthController.class).home();
		}
		
		public void unauthorized(@Observes AuthorizationFailedEvent ev){
			result.redirectTo(AuthController.class).unauthorized();
		}
		
		
	
	}
	

# configuration

There are default configurations for Login and Logout urls. In order to start
login process, user has to access /login url and /logout for logout process. If
you want to change these configurations, just create context-param entries in
web.xml:

	<context-param>
		<param-name>loginUrl</param-name>
		<param-value>/login</param-value>
	</context-param>

	<context-param>
		<param-name>logoutUrl</param-name>
		<param-value>/logout</param-value>
	</context-param>

# AuthProvider 

You need to declare a Provider that is responsible for authentication. There
are two steps. First you need to create a class that implements AuthProvider:

    @RequestScoped
    public class CustomDBProvider implements AuthProvider {

        @Inject
        private EntityManager em;

        @Override
        public Principal authenticate(String login, String password) throws Exception {
            try {
                return em.createQuery("from User u where u.login = :login and u.password = :password", User.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();
            } catch(Exception e) {
                return null;
            }
        }

    }

Note that the class User must implement the Authenticble interface.
It is also possible to use a provider which is already implemented by VAAS. For
example, JAASProvider.

Now you need to explain to VAAS to use this AuthProvider. Just create a
configuration class:

    public class VaasConfiguration implements ProviderConfiguration {
    
        @Override
        public AuthProviders providers() {
            return new AuthProviders(CustomDBProvider.class);
        }
    
    }

If you have more than one provider, you could pass a varargs of Class<? extends
AuthProvider> and the authentication would stop in the first success. 

# protecting URL's

The URL protection on VAAS is based on Rules definitions. Instead of associate Roles as ADMIN,MANAGER and GUEST
you associate objects that protect URLs. For example, here's a simple Rule definition:

    @SessionScoped
    public class VaasRoleRule implements Rule {
        
        @Inject		
        private VaasSession userSession;
        @Inject
        private Collection<Group> rolesAllowed;
        
        @Override
        public boolean isAuthorized() {
            
            if (rolesAllowed.isEmpty()) {
                return true;
            }
            
            boolean valid = false;
    
            if (userSession.isLogged()) {
                Authenticable user = (Authenticable) userSession.getLoggedUser();	
                
                for (Group role : rolesAllowed) {
                    valid = valid || user.getGroups().contains(role);
                }
            }
    
            return valid;
        }
    
    }

Here we have a very simple Rule based on simple Roles. This is a pre-defined Rule of VAAS that is ready
to use if you follow our interfaces. We have a JAASRule too, if you use JAAS as implementation of this part
of your system. You can define as many Rules as you want.

Now you have to bind URLs and Rules. 

	@ApplicationScoped
	public class JAASSecurityRoutes implements RulesConfiguration {
	
		@Inject
		private LoggedRule loggedRule;
		@Inject
		private HttpServletRequest request;
		
		public RulesByURL rulesByURL() {
            RulesByURL rulesByURL = new RulesByURL();
            rulesByURL.defaultRule(loggedRule)
                .add("/main", new JAASRolesRule(request,"ROLE_USER","ROLE_ADMIN"))
                .add("/user-page", new JAASRolesRule(request,"ROLE_USER","ROLE_ADMIN"))
                .add("/admin-page", new JAASRolesRule(request,"ROLE_USER","ROLE_ADMIN"));
            return rulesByURL;
		}
	
	}
	
Above we have a static configuration example. Now let's see a Database configuration example.

    @ApplicationScoped
    public class CustomRuleConfiguration implements RulesConfiguration {
        
        @Inject
        private EntityManager em;
        
        @Inject
        private LoggedRule loggedRule;
        
        @Inject
        private VaasSession userSession;
        
        private RulesByURL rulesByURL = new RulesByURL(); 
        
        @PostConstruct
        public void init(){
            rulesByURL.defaultRule(loggedRule);
            List<URLAccessRole> accessRoles = em.createQuery("from URLAccessRole", URLAccessRole.class).getResultList();
            for (URLAccessRole urlAccessRole : accessRoles) {			
                rulesByURL.add(urlAccessRole.getUrl(), new VaasRoleRule(userSession, urlAccessRole.getAllowedRoles()));
            }
        }
        
        public RulesByURL rulesByURL() {
            return rulesByURL;
        }
    
    }
		
For us this is the big thing of vaas. You can decide the way you configure your
URL's protection. Static, database based or whatever you want. Just implement
RulesConfiguration and this configuration will be used.

# listening authentication and authorization events

Now we have to handle auth events. Let's see an example.

    public class AuthListener {
        
        @Inject
        private Result result;
        
        @Inject
        private HttpServletRequest request;
        
        public void login(@Observes AuthenticatedEvent event) {
            Principal userPrincipal = event.getUserPrincipal();
            result.redirectTo(AppController.class).main();
        }
        
        public void loginFailed(@Observes AuthenticateFailedEvent event) {
            if (event.hasErrors()) {
                String message = event.getExceptions().get(0).getMessage(); // just the first message
                result.include("message",message);
            }
            result.redirectTo(AuthController.class).home();
        }
        
        public void logout(@Observes LogoutEvent event) throws ServletException {
            request.logout();
            result.redirectTo(AuthController.class).home();
        }
        
        public void unauthorized(@Observes AuthorizationFailedEvent ev) {		
            result.redirectTo(AuthController.class).unauthorized();
        }
                            
    }
		
Just a simple class that uses CDI events!

# future plans

We would love to support other frameworks like JSF. VAAS is not based on VRaptor :). It is a CDI
based project that can be integrated with any CDI based web framework.

# help

Get help from vaas/vraptor developers and the community at vraptor mailing list.
