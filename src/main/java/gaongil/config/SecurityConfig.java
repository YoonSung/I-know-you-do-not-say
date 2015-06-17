package gaongil.config;

import gaongil.security.SecurityRememberMeService;
import gaongil.security.SecurityUserDetailService;
import gaongil.support.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages={"gaongil.security", "gaongil.service"})
@PropertySource("classpath:/application.properties")
@ImportResource({"classpath:/springSecurity.xml"})
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
// WebSecurityConfigureAdapter is provides a default configuration in the configre(HttpSecurity http) method.
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	Environment environment;

	/**
	 * @author yoon
	 * Doen't Whatever method name. but @Autowired and AuthenticationManagerBuilder parameter is required
	 * Authentication Code inside
	 */
	@Autowired
    public void setupAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.authenticationProvider(daoAuthenticationProvider())
			.authenticationProvider(rememberMeAuthenticationProvider())
			.userDetailsService(securityUserDetailService())
			.passwordEncoder(passwordEncoder);

    }

	/**
	 * @author yoon
	 * Authorization Code inside
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// ~ Setting
		http
				//.addFilterBefore(rememberMeAuthenticationFilter(), BasicAuthenticationFilter.class )
				.rememberMe()
				.rememberMeServices(securityRememberMeService())

				//[test start]
				.and().formLogin()
				.and().httpBasic()
				//[test end]

				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
				.and().csrf().disable();
				//.and()
				//.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint())


		// ~ Resource Authorization
		http
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/user").permitAll()
				.antMatchers("/login", "/about").permitAll()
	        	.antMatchers("/main").hasRole("MEMBER")
	        	.anyRequest().authenticated();
	        	
	}

	@Bean
	public SecurityUserDetailService securityUserDetailService() {
		return new SecurityUserDetailService();
	}

	@Bean
	public SecurityRememberMeService securityRememberMeService() {

		String key = environment.getProperty(Constant.PROPERTY_KEY_REMEMBERME_TOKEN_KEY);
		String cookieName = environment.getProperty(Constant.PROPERTY_KEY_AUTH_COOKIE_NAME);
		int tokenValiditySeconds = Integer.parseInt(environment.getProperty(Constant.PROPERTY_KEY_AUTH_VALIDITY_SECONDS));

		SecurityRememberMeService securityRememberMeService = new SecurityRememberMeService(key, securityUserDetailService());
		securityRememberMeService.setCookieName(cookieName);
		securityRememberMeService.setTokenValiditySeconds(tokenValiditySeconds);
		securityRememberMeService.setAlwaysRemember(true);

		return securityRememberMeService;
	}

	@Bean
	public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {

		String key = environment.getProperty(Constant.PROPERTY_KEY_REMEMBERME_TOKEN_KEY);
		return new RememberMeAuthenticationProvider(key);
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {

		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setUserDetailsService(securityUserDetailService());

		return daoAuthenticationProvider;
	}
}
