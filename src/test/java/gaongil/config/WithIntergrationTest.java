package gaongil.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.mapper.factory.Jackson2ObjectMapperFactory;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.PostConstruct;

import static com.jayway.restassured.config.ObjectMapperConfig.objectMapperConfig;

/**
 * Created by yoon on 15. 6. 19..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {IntergrationTestConfig.class})
public class WithIntergrationTest {

    @Rule
    @Autowired
    public WithTokenRule tokenRule;

    public WithIntergrationTest() {
        init();

    }

    public void init() {
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(objectMapperConfig().jackson2ObjectMapperFactory(
                new Jackson2ObjectMapperFactory() {
                    @Override
                    public ObjectMapper create(Class aClass, String s) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                        //objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);

                        return objectMapper;
                    }
                }
        ));
    }

    protected RequestSpecification given(WithTokenRule.TYPE type) {
        return this.tokenRule.given(type);
    }

    protected RequestSpecification given() {
        return RestAssured.given();
    }
}
