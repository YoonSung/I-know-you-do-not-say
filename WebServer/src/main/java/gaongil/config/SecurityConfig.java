package gaongil.config;

import gaongil.security.SecurityRememberMeService;
import gaongil.security.SecurityUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@ComponentScan(basePackages={"gaongil.security", "gaongil.service"})
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
// WebSecurityConfigureAdapter is provides a default configuration in the configre(HttpSecurity http) method.
	
	@Autowired
	SecurityUserDetailService securityUserDetailService;
	
	@Bean
	public SecurityRememberMeService securityRememberMeService() {
		
		SecurityRememberMeService instance = new SecurityRememberMeService("test", securityUserDetailService);
		instance.setAlwaysRemember(true);
		instance.setTokenValiditySeconds(360);
		
		return instance;
	}
	
	/**
	 * Doen't Whatever method name.
	 * 
	 */
	
	//TODO Extract XML for environment
	
	@Autowired
    public void setupAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        /*
         	auth
            .inMemoryAuthentication()
                .withUser("user").password("123").roles("USER")
                .and()
                .withUser("member").password("321").roles("MEMBER");
		*/
		auth
			.userDetailsService(securityUserDetailService)
			//for test
			.passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.rememberMe()
				//.addFilterBefore(rememberMeAuthenticationFilter(), BasicAuthenticationFilter.class )
				//TODO extract key to xml file
				.rememberMeServices(securityRememberMeService()).key("test").and()
				
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				
				.authorizeRequests()
				.antMatchers("/login","/about").permitAll()
	        	.antMatchers("/main").hasRole("MEMBER")
	        	.anyRequest().authenticated()
	        	.and()
	        .formLogin()
	        	.permitAll();
	}

	/*
	private SecurityAuthenticationFilter rememberMeAuthenticationFilter() {
		
		return null;
	}
	*/
	
	/*
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
	*/
}
