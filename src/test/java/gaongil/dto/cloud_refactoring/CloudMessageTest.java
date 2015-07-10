package gaongil.dto.cloud_refactoring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gaongil.config.WebConfig;
import gaongil.dto.cloud_refactoring.client.ClientDTO;
import gaongil.dto.cloud_refactoring.client.PlainText;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by yoon on 15. 7. 10..
 */
public class CloudMessageTest {

    private static ObjectMapper objectMapper;

    @BeforeClass
    public static void setUp() {
        objectMapper = new WebConfig().objectMapper();
    }

    @Test
    public void format() throws JsonProcessingException {
        CloudMessage message = new CloudMessage(Strategy1.CHAT_MESSAGE, new PlainText("testesetests"));
        System.out.println(objectMapper.writeValueAsString(message));
    }

    public String getJsonString() throws JsonProcessingException {
        CloudMessage message = new CloudMessage(Strategy1.CHAT_MESSAGE, new PlainText("testesteste"));
        return objectMapper.writeValueAsString(message);
    }

    @Test
    public void deserialize() throws IOException {

        String json = getJsonString();
        System.out.println(json);

        CloudMessage message = objectMapper.readValue(json, CloudMessage.class);
        System.out.println(message.toString());

        ClientStrategy clientStrategy = message.getStrategy();

        ((ClientDTO) objectMapper.convertValue(message.getData(), clientStrategy.getDTO())).process(clientStrategy);
        //System.out.println(objectMapper.convertValue(message.getData(), clientStrategy.getDTO()));
        //System.out.println(objectMapper.writeValueAsString(objectMapper.convertValue(message.getData(), clientStrategy.getDTO())));
    }
}