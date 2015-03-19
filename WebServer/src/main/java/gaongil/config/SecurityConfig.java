package gaongil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
// WebSecurityConfigureAdapter is provides a default configuration in the configre(HttpSecurity http) method.
	
	/**
	 * Doen't Whatever method name.
	 * 
	 */
	@Autowired
    public void setupAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("123").roles("USER")
                .and()
                .withUser("member").password("321").roles("MEMBER");
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
	      .authorizeRequests()
	        .antMatchers("/login","/about").permitAll() // #4
	        .antMatchers("/main").hasRole("MEMBER") // #6
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
