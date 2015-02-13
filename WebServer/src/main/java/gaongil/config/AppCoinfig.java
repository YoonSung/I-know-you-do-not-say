package gaongil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class AppCoinfig {
	
	@Autowired
	CcsConfig ccsConfig;
	
	@Autowired
	DBConfig dbConfig;
	
	@Autowired
	WebConfig webConfig;
}