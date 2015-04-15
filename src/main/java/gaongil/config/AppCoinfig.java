package gaongil.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 각각의 Configuration File을 Import한다.
 */
@Import(value={
			CcsConfig.class, 
			DBConfig.class, 
			WebConfig.class,
			SecurityConfig.class
		}
)
@Configuration
public class AppCoinfig {
}