package gaongil.web;

import gaongil.ccs.GcmCcsSender;
import gaongil.domain.User;
import gaongil.dto.cloud.CloudMessage;
import gaongil.dto.cloud.Type1;
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

        User user = new User(null, null, null, null, "fYhXGqsNZfs:APA91bE4UYUn2013RD7mAGR_zqn68Ya2SsHg1ebaIcsmVzVkfnm6KZLGgk2wPhRu6O796GcJEucHq_VI8DNPhHyOrS6sOkXodF-VOYEwJk6CNy7y-cfrgkDAWrqlAPGekgpSYLnV8Olm", null);
        CloudMessage cloudMessage = CloudMessage.createType1(Type1.SubType.CHAT_MESSAGE, message);
        gcmCcsSender.send(user, cloudMessage);

        return "Send Message Success";
    }

}
