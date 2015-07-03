package controller;

import gaongil.domain.User;
import gaongil.dto.ChatRoomDTO;
import gaongil.service.GroupService;
import gaongil.support.web.resolver.argument.LoginUser;
import gaongil.support.web.resolver.argument.Response;
import gaongil.support.web.resolver.argument.ResponseApplicationCode;
import gaongil.support.web.status.ApplicationCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yoon on 15. 7. 1..
 */
@RestController
@RequestMapping("/groups")
public class GroupController {

    private Logger log = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    GroupService groupService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ChatRoomDTO create(@LoginUser User currentUser, @RequestBody ChatRoomDTO requestedChatRoom, @Response ResponseApplicationCode code) {
        log.debug("GroupForm : {}", requestedChatRoom.toString());
        log.debug("LoginUser : {}", currentUser.getId());

        ChatRoomDTO newChatRoom = groupService.create(currentUser, requestedChatRoom);
        code.set(ApplicationCode.CREATE_NEWDATA);
        return newChatRoom;
    }
}
