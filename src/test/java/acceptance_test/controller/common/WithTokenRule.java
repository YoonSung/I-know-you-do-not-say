package acceptance_test.controller.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.mapper.factory.Jackson2ObjectMapperFactory;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.specification.RequestSpecification;
import gaongil.config.SecurityConfig;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.jayway.restassured.config.ObjectMapperConfig.objectMapperConfig;

/**
 * Created by yoon on 15. 6. 19..
 */
public class WithTokenRule implements TestRule {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TEST_SERVER_BASE_URL = "http://localhost:8080";

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        RestAssured.defaultParser = Parser.JSON;
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(objectMapperConfig().jackson2ObjectMapperFactory(
                new Jackson2ObjectMapperFactory() {
                    @Override
                    public ObjectMapper create(Class aClass, String s) {
                        //objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
                        return objectMapper;
                    }
                }
        ));
    }

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
            return TEST_SERVER_BASE_URL+url;
        }

        void setToken(String token) {
            this.token = token;
        }

        String getToken() {
            return token;
        }
    }

    public RequestSpecification given(TYPE type) {

        if (type.getToken() == null) {
            try {
                TYPE.USER.setToken(generateUserTokenFromServer());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Generating Token Failure");
            }
            //TODO
            //memberToken = generateMemberTokenFromServer();
        }

        return RestAssured.given().cookie(SecurityConfig.getAuthCookieName(), type.getToken());
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

    private String generateUserTokenFromServer() throws Exception {
        return generateUserTokenByRestTemplate();
    }

    private String generateUserTokenByRestTemplate() {

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
            if (cookieEntry.startsWith(SecurityConfig.getAuthCookieName())) {
                return cookieEntry.replace(SecurityConfig.getAuthCookieName()+"=","");
            }
        }

        throw new RuntimeException("Generating Token Failure");
    }


}
