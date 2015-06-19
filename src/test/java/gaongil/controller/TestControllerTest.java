package gaongil.controller;

import gaongil.config.AppConfig;
import gaongil.config.TestConfig;
import gaongil.config.WithTokenRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by yoon on 15. 6. 19..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
public class TestControllerTest {

    @Rule
    @Autowired
    public WithTokenRule tokenRule;

    @Test
    public void 홈() {
        tokenRule.given(WithTokenRule.TYPE.USER).
        when().
            get("/").
        then().
            body("code", equalTo(200)).
            //body("message", equalTo("요청성공")).
            body("data", equalTo("Server Operation Success!"));
    }
}