package util;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import gaongil.config.WebConfig;

import org.springframework.test.web.servlet.MockMvc;

public class TestUtil {

	public static MockMvc getMockMvc(Object controller) {
		WebConfig webConfig = new WebConfig();
		
		return standaloneSetup(controller)
			
			.setViewResolvers(
					webConfig.resourceViewResolver(), 
					webConfig.contentNegotiationViewResolver()
			)
			
		
			.setMessageConverters(webConfig.mappingJackson2HttpMessageConverter())
			.setCustomArgumentResolvers(
					webConfig.loginMemberHandlerMethodArgument(), 
					webConfig.loginUserHandlerMethodArgument(),
					webConfig.responseCodeHandlerMethodArgument()).build();
			/*
			.setHandlerExceptionResolvers(webConfig.customExceptionResolver()).build();
			*/
	}

	
	
}
