package gaongil.config;

import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by yoon on 15. 6. 19..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {IntergrationTestConfig.class})
public class WithIntergrationTest {

    @Rule
    @Autowired
    public WithTokenRule tokenRule;

    protected RequestSpecification given(WithTokenRule.TYPE type) {
        return this.tokenRule.given(type);
    }
}
