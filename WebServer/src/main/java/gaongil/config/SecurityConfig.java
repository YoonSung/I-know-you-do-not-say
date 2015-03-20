package gaongil.config;

import javax.annotation.Resource;

import gaongil.security.SecurityUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@ComponentScan(basePackages={"gaongil.security", "gaongil.service"})
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
// WebSecurityConfigureAdapter is provides a default configuration in the configre(HttpSecurity http) method.
	
	@Resource
	SecurityUserDetailService securityUserDetailService;
	
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
				.authorizeRequests()
				.antMatchers("/login","/about").permitAll()
	        	.antMatchers("/main").hasRole("MEMBER")
	        	.anyRequest().authenticated()
	        	.and()
	        .formLogin()
	        	.permitAll();
	}
	
	/*
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
	*/
}
