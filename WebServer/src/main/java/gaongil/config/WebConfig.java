package gaongil.config;

import gaongil.support.web.LoginMemberHandlerMethodArgumentResolver;
import gaongil.support.web.LoginUserHandlerMethodArgument;

import java.util.EnumSet;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages={"gaongil.controller"})
public class WebConfig extends WebMvcConfigurationSupport implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(AppCoinfig.class);
		
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
		dispatcher.addMapping("/");
		dispatcher.setLoadOnStartup(1);
		
		servletContext.addListener(new ContextLoaderListener(rootContext));
		
		
		/**
		 * Add Spring Security Filter.
		 * if not setting to this.
		 * 
		 * See Document (chapter 3. Java Configuration) - http://docs.spring.io/spring-security/site/docs/3.2.6.RELEASE/reference/htmlsingle/#hello-web-security-java-configuration
		 * implements AbstractSecurityWebApplicationInitializer, and it enroll automatically below settings
		 */
		Dynamic securityFilter = servletContext.addFilter(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, DelegatingFilterProxy.class);
		securityFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
	}
	
	@Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }
	
	@Bean
	public HandlerMethodArgumentResolver loginMemberHandlerMethodArgument() {
		return new LoginMemberHandlerMethodArgumentResolver();
	}
	
	@Bean
	public HandlerMethodArgumentResolver loginUserHandlerMethodArgument() {
		return new LoginUserHandlerMethodArgument();
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(loginUserHandlerMethodArgument());
		argumentResolvers.add(loginMemberHandlerMethodArgument());
		
		super.addArgumentResolvers(argumentResolvers);
	}
}