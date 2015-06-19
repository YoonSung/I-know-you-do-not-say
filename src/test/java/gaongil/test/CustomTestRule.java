package gaongil.test;

import gaongil.service.MemberService;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yoon on 15. 6. 19..
 */
public class CustomTestRule implements TestRule {

    private static int INDEX = 0;

    @Autowired
    private MemberService memberService;

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                System.out.println(memberService.findByEmail("lvev9925@naver.com"));
                base.evaluate();
                System.out.println("CustomTestRule Index : "+CustomTestRule.INDEX);
            }
        };
    }
}
