package acceptance_test.controller.common;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Rule;

import java.nio.charset.Charset;

/**
 * Created by yoon on 15. 6. 19..
 */
public class WithIntergrationTest {

    @Rule
    public WithTokenRule tokenRule = WithTokenRule.getInstance();

    protected RequestSpecification given(WithTokenRule.TYPE type) {
        return addOption(this.tokenRule.given(type));
    }

    protected RequestSpecification given() {
        return addOption(RestAssured.given());
    }

    private RequestSpecification addOption(RequestSpecification specification) {
        return specification.contentType(ContentType.JSON.withCharset("UTF8"));
    }

    protected <T> T casting(Object data, Class<T> chatRoomDTOClass) {
        return WithTokenRule.WITH_OBJECT_MAPPER.convertValue(data, chatRoomDTOClass);
    }
}
