package gaongil.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;
import gaongil.support.web.converter.CustomMappingJackson2HttpMessageConverter;
import gaongil.support.web.converter.ResponseMessageConverter;
import gaongil.support.web.holder.RequestHolder;
import gaongil.support.web.resolver.CustomExceptionResolver;
import gaongil.support.web.resolver.LoginMemberHandlerMethodArgumentResolver;
import gaongil.support.web.resolver.LoginUserHandlerMethodArgumentResolver;
import gaongil.support.web.resolver.ResponseCodeHandlerMethodArgumentResolver;
import gaongil.support.web.resolver.argument.ResponseApplicationCode;

import java.util.ArrayList;
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
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan(basePackages={"gaongil.web"})
public class WebConfig extends WebMvcConfigurationSupport implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(AppConfig.class);
		
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
	public RequestHolder requestHolder() {
		return new RequestHolder();
	}
	
	/**
	 * argument resolver parameter 
	 */
	@Bean
	public ResponseApplicationCode responseApplicationCode() {
		return new ResponseApplicationCode();
	}
	
	//~ MessageConverter ========================================================================================================
	
	@Bean
	public ResponseMessageConverter responseMessageConverter() {
		return new ResponseMessageConverter();
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		return new CustomMappingJackson2HttpMessageConverter();
	}
	
	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJackson2HttpMessageConverter());
		//addDefaultHttpMessageConverters(converters);
		super.configureMessageConverters(converters);
	}
	
	
	//~ ViewResolver ========================================================================================================
	
	//http://fruzenshtein.com/spring-java-configurations/
	/*
	@Override
	protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer
			//.mediaType("html", MediaType.TEXT_HTML)
			.mediaType("json", MediaType.APPLICATION_JSON)
			.defaultContentType(MediaType.APPLICATION_JSON);
	}
	 */
	 
	
	@Bean
	public ViewResolver resourceViewResolver() {
		//TODO DELETE
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(1);
        
        return resolver;
	}

	@Bean
	public View mappingJackson2JsonView() {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		//view.setPrettyPrint(true);
		view.setObjectMapper(objectMapper());
		return view;
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		mapper.getSerializationConfig().getDefaultVisibilityChecker()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE);

		return mapper;
	}
	
	@Bean
    public ViewResolver contentNegotiationViewResolver() {
		
		List<View> views = new ArrayList<View>();
		views.add(mappingJackson2JsonView());
                
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setDefaultViews(views);
        resolver.setOrder(1);
        return resolver ;
    }
	//~ ExceptionResolver ========================================================================================================
	
	@Bean
	public CustomExceptionResolver customExceptionResolver() {
		CustomExceptionResolver resolver = new CustomExceptionResolver();
		resolver.setOrder(100);
		return resolver;
	}
	
	protected void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(customExceptionResolver());
		
		super.configureHandlerExceptionResolvers(exceptionResolvers);
	};
	
	//~ ArgumentResolver ========================================================================================================
	
	@Bean
	public HandlerMethodArgumentResolver loginMemberHandlerMethodArgument() {
		return new LoginMemberHandlerMethodArgumentResolver();
	}
	
	@Bean
	public HandlerMethodArgumentResolver loginUserHandlerMethodArgument() {
		return new LoginUserHandlerMethodArgumentResolver();
	}
	
	@Bean
	public HandlerMethodArgumentResolver responseCodeHandlerMethodArgument() {
		return new ResponseCodeHandlerMethodArgumentResolver();
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(loginUserHandlerMethodArgument());
		argumentResolvers.add(loginMemberHandlerMethodArgument());
		argumentResolvers.add(responseCodeHandlerMethodArgument());
		
		super.addArgumentResolvers(argumentResolvers);
	}
}