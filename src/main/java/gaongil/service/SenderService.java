package gaongil.service;

import gaongil.ccs.GcmCcsSender;
import gaongil.domain.*;
import gaongil.dto.cloud.CloudMessage;
import gaongil.dto.cloud.DialogForm;
import gaongil.dto.cloud.Type4;
import gaongil.support.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoon on 15. 7. 7..
 */
@Service
public class SenderService {

    @Autowired
    GcmCcsSender gcmSender;

    @Autowired
    UserService userService;


    // TODO Refactoring, seperate method, Constant String
    public void inviteToGroup(ChatRoom chatRoom) {

        List<User> alreadyRegisteredUser = new ArrayList<>();

        User currentUser = null;

        // Add this function to chatRoom
        for (ChatRoomSetting domain : chatRoom.getChatRoomSettings()) {
            if (domain.getStatus() == InvitationStatus.WAIT_USER_CONFIRM)
                alreadyRegisteredUser.add(domain.getId().getUser());

            else if (domain.getStatus() == InvitationStatus.JOIN)
                currentUser = domain.getId().getUser();
        }

        DialogForm dialogForm = new DialogForm(currentUser, "'%s'님이 '%s' 그룹에 추가했습니다.\n초대에 응하시겠습니까?", "거절", "수락", "/groups/%n");
        CloudMessage message = CloudMessage.createType4(Type4.SubType.GROUP_INVITATION, dialogForm);
        gcmSender.sends(alreadyRegisteredUser, message);
    }
}
