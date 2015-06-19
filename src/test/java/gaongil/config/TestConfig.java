package gaongil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by yoon on 15. 6. 19..
 */
@Import(value={AppConfig.class})
@Configuration
public class TestConfig {

    @Bean
    public WithTokenRule tokenRule() {
        return new WithTokenRule();
    }
}
