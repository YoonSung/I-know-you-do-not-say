package gaongil.config;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;
import gaongil.security.SecurityRememberMeService;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by yoon on 15. 6. 19..
 */
public class WithTokenRule implements TestRule {

    public enum TYPE {
        USER("/user"),
        //TODO change url
        MEMBER("/member");

        String url;
        TYPE(String url) {
            this.url = url;
        }

        public String getUrl() {
            return "http://localhost:8080"+url;
        }
    }

    private String userToken;
    private String memberToken;

    @PostConstruct
    public void initToken() {
        userToken = generateUserTokenFromServer();

        //TODO
        //memberToken = generateMemberTokenFromServer();
    }

    public RequestSpecification given(TYPE type) {
        return RestAssured.given().cookie("WITH_AUTH", this.userToken);
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

        return getTokenFromServer("http://localhost:8080/user", parameters);
    }

    private String getTokenFromServer(String url, MultiValueMap parameters) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, parameters, String.class);
        HttpHeaders headers = response.getHeaders();
        String cookies = headers.get(HttpHeaders.SET_COOKIE).get(0);
        String[] cookieEntries = cookies.split(";");

        for (String cookieEntry : cookieEntries) {
            cookieEntry = cookieEntry.trim();
            if (cookieEntry.startsWith("WITH_AUTH")) {
                return cookieEntry.replace("WITH_AUTH=","");
            }
        }

        throw new RuntimeException("Generating Token Failure");
    }
}
