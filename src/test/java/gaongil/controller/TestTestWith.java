package gaongil.controller;

import gaongil.config.WithIntergrationTest;
import gaongil.config.WithTokenRule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by yoon on 15. 6. 19..
 */
public class TestTestWith extends WithIntergrationTest {

    @Test
    public void 홈() {
        given(WithTokenRule.TYPE.USER).
        when().
            get("/").
        then().
            body("code", equalTo(200)).
            //body("message", equalTo("요청성공")).
            body("data", equalTo("Server Operation Success!"));
    }
}