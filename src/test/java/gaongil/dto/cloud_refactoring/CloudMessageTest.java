package gaongil.dto.cloud_refactoring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gaongil.config.WebConfig;
import gaongil.dto.cloud.*;
import gaongil.dto.cloud_refactoring.client.PlainText;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void deserialize() {
        Strategy1 strategy = Strategy1.CHAT_MESSAGE;

        String json = "{text : 'testest'}";

        PlainText dto = (PlainText) getDTOFromString(json, strategy.getDto());

        System.out.println(dto.toString());
    }

    private <T> T getDTOFromString(String json, Class<T> type) {
        return objectMapper.convertValue(json, type);
    }
}