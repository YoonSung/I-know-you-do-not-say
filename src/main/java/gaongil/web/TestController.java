package gaongil.web;

import gaongil.ccs.GcmCcsSender;
import gaongil.domain.User;
import gaongil.dto.cloud.CloudMessage;
import gaongil.dto.cloud.Strategy1;
import gaongil.dto.cloud.client.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yoon on 15. 7. 9..
 */
@Controller
public class TestController {

    @Autowired
    private GcmCcsSender gcmCcsSender;

    @RequestMapping("/test/{message}")
    public @ResponseBody String index(@PathVariable String message) {

        User user = new User(null, null, null, null, "e79j_1u8-9Q:APA91bFbp2kmFPa_ZaBKLanAigKlz5_UMtABFdUEjne_hcyv9TwldbAXJSZL0guBwd_T7eZdjN-fUxTrwKIZL6xCV9fdifbZf1KAWKBaGdMZc8M-Btkcki3SC54cIz65pBTZb-R4iXzd", null);
        CloudMessage cloudMessage = new CloudMessage(Strategy1.CHAT_MESSAGE, new PlainText(message));
        gcmCcsSender.send(user, cloudMessage);

        return "Send Message Success";
    }

}
