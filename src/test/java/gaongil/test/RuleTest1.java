package gaongil.test;

import gaongil.config.AppConfig;
import gaongil.config.TestConfig;
import org.junit.Rule;
import org.junit.Test;
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
@ContextConfiguration(classes = {TestConfig.class})
public class RuleTest1 {

    @Rule
    @Autowired
    public CustomTestRule customTestRule;

    @Test
    public void test1() {
        System.out.println("RuleTest1 - test1");
    }

    @Test
    public void test2() {
        System.out.println("RuleTest1 - test2");
    }

    @Test
    public void test3() {
        System.out.println("RuleTest1 - test3");
    }
}
