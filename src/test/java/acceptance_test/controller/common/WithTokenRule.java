package acceptance_test.controller.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.mapper.factory.Jackson2ObjectMapperFactory;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.specification.RequestSpecification;
import gaongil.config.WebConfig;
import gaongil.dto.UserDTO;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.config.ObjectMapperConfig.objectMapperConfig;

/**
 * Created by yoon on 15. 6. 19..
 */
public class WithTokenRule implements TestRule {

    public static ObjectMapper WITH_OBJECT_MAPPER;
    private static final String TEST_SERVER_BASE_URL = "http://localhost:8080";

    private final String PHONENUMBER_KEY = "phoneNumber";
    private final String PHONENUMBER_VALUE = "01099258547";
    private final String REG_ID_KEY = "regId";
    private final String REG_ID_VALUE = "testRegId";
    private final String UUID_KEY = "uuid";
    private final String UUID_VALUE = "testUuid";

    //intergration
    public static final String AUTH_COOKIE_NAME = "WITH_AUTH";

    static {
        WITH_OBJECT_MAPPER = new WebConfig().objectMapper();
        
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(objectMapperConfig().jackson2ObjectMapperFactory(
                new Jackson2ObjectMapperFactory() {
                    @Override
                    public ObjectMapper create(Class aClass, String s) {
                        //WITH_OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
                        return WITH_OBJECT_MAPPER;
                    }
                }
        ));
    }

    private WithTokenRule(){}

    private static WithTokenRule instance = new WithTokenRule();

    public static WithTokenRule getInstance() {
        return instance;
    }

    public enum TYPE {
        USER("/users"),
        //TODO change url
        MEMBER("/members");

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
                //TYPE.USER.setToken(generateUserTokenByJsonParameter());
                TYPE.USER.setToken(generateUserTokenByPostParameter());

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Generating Token Failure");
            }
            //TODO
            //memberToken = generateMemberTokenFromServer();
        }

        return RestAssured.given().cookie(AUTH_COOKIE_NAME, type.getToken());
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

    private String generateUserTokenByJsonParameter() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setPhoneNumber(PHONENUMBER_VALUE);
        dto.setRegId(REG_ID_VALUE);
        dto.setUuid(UUID_VALUE);

        return getTokenFromServerByJsonParameter(TYPE.USER.getUrl(), WITH_OBJECT_MAPPER.writeValueAsString(dto));
    }

    private String getTokenFromServerByJsonParameter(String url, String parameter) throws JsonProcessingException {

        //request
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(parameter, requestHeader);


        //response
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        HttpHeaders responseHeaders = response.getHeaders();
        String cookies = responseHeaders.get(HttpHeaders.SET_COOKIE).get(0);
        String[] cookieEntries = cookies.split(";");

        for (String cookieEntry : cookieEntries) {
            cookieEntry = cookieEntry.trim();
            if (cookieEntry.startsWith(AUTH_COOKIE_NAME)) {
                return cookieEntry.replace(AUTH_COOKIE_NAME+"=","");
            }
        }

        throw new RuntimeException("Generating Token Failure");
    }


    private String generateUserTokenByPostParameter() throws Exception {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add(PHONENUMBER_KEY, PHONENUMBER_VALUE);
        parameters.add(REG_ID_KEY, REG_ID_VALUE);
        parameters.add(UUID_KEY, UUID_VALUE);

        return getTokenFromServerByPostParameter(TYPE.USER.getUrl(), parameters);
    }

    private String getTokenFromServerByPostParameter(String url, MultiValueMap parameters) throws JsonProcessingException {

        //request
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        //response
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        HttpHeaders responseHeaders = response.getHeaders();
        String cookies = responseHeaders.get(HttpHeaders.SET_COOKIE).get(0);
        String[] cookieEntries = cookies.split(";");

        for (String cookieEntry : cookieEntries) {
            cookieEntry = cookieEntry.trim();
            if (cookieEntry.startsWith(AUTH_COOKIE_NAME)) {
                return cookieEntry.replace(AUTH_COOKIE_NAME+"=","");
            }
        }

        throw new RuntimeException("Generating Token Failure");
    }
}
