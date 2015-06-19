package gaongil.config;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;
import gaongil.support.Constant;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * Created by yoon on 15. 6. 19..
 */
@ImportResource(value = {"application.properties"})
public class WithTokenRule implements TestRule {

    @Autowired
    private Environment environment;
    private String authCookieName;

    public enum TYPE {
        USER("/user"),
        //TODO change url
        MEMBER("/member");

        String url;
        String token;

        TYPE(String url) {
            this.url = url;
        }

        String getUrl() {
            return IntergrationTestConfig.TEST_SERVER_BASE_URL+url;
        }

        void setToken(String token) {
            this.token = token;
        }

        String getToken() {
            return token;
        }
    }

    @PostConstruct
    public void initToken() {
        this.authCookieName = environment.getProperty(Constant.PROPERTY_KEY_AUTH_COOKIE_NAME);

        TYPE.USER.setToken(generateUserTokenFromServer());

        //TODO
        //memberToken = generateMemberTokenFromServer();
    }

    public RequestSpecification given(TYPE type) {
        return RestAssured.given().cookie(authCookieName, type.getToken());
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                base.evaluate();
            }
        };
    }

    //TODO
    private String generateMemberTokenFromServer() {
        return null;
    }

    private String generateUserTokenFromServer() {

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("phoneNumber", "01099258547");
        parameters.add("regId", "testRegId");
        parameters.add("uuid", "testUuid");

        return getTokenFromServer(TYPE.USER.getUrl(), parameters);
    }

    private String getTokenFromServer(String url, MultiValueMap parameters) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, parameters, String.class);
        HttpHeaders headers = response.getHeaders();
        String cookies = headers.get(HttpHeaders.SET_COOKIE).get(0);
        String[] cookieEntries = cookies.split(";");

        for (String cookieEntry : cookieEntries) {
            cookieEntry = cookieEntry.trim();
            if (cookieEntry.startsWith(this.authCookieName)) {
                return cookieEntry.replace(this.authCookieName+"=","");
            }
        }

        throw new RuntimeException("Generating Token Failure");
    }
}
