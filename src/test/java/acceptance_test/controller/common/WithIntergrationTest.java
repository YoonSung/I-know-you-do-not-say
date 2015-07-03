package acceptance_test.controller.common;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Rule;

/**
 * Created by yoon on 15. 6. 19..
 */
public class WithIntergrationTest {

    @Rule
    public WithTokenRule tokenRule = WithTokenRule.getInstance();

    protected RequestSpecification given(WithTokenRule.TYPE type) {
        return this.tokenRule.given(type).contentType(ContentType.JSON);
    }

    protected RequestSpecification given() {
        return RestAssured.given().contentType(ContentType.JSON);
    }
}
