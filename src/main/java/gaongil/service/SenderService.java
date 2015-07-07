package gaongil.service;

import gaongil.ccs.GcmCcsSender;
import gaongil.domain.*;
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

    public void inviteToGroup(ChatRoom chatRoom) {

        List<User> alreadyRegisteredUser = new ArrayList<>();

        // Add this function to chatRoom
        for (ChatRoomSetting domain : chatRoom.getChatRoomSettings()) {
            if (domain.getStatus() == InvitationStatus.WAIT_USER_CONFIRM)
                alreadyRegisteredUser.add(domain.getId().getUser());
        }

        // TODO CloudMessage - Type4
        //gcmSender.sends(alreadyRegisteredUser, );
    }
}
