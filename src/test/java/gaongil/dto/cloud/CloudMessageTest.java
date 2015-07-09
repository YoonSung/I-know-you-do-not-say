package gaongil.dto.cloud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gaongil.config.WebConfig;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yoon on 15. 7. 9..
 */
public class CloudMessageTest {

    private static ObjectMapper objectMapper;

    @BeforeClass
    public static void setUp() {
        objectMapper = new WebConfig().objectMapper();
    }

    @Test
    public void format() throws JsonProcessingException {
        CloudMessage message = CloudMessage.createType1(Type1.SubType.CHAT_MESSAGE, "test");
        System.out.println(objectMapper.writeValueAsString(message));
    }
}