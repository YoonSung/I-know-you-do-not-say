package gaongil.service;

import gaongil.domain.ChatRoom;
import gaongil.dto.ChatRoomDTO;
import gaongil.support.exception.WrongParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yoon on 15. 7. 1..
 */
@Service
public class GroupService {

    private Logger log = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    SenderService messageSenderService;

    public ChatRoom create(ChatRoomDTO chatRoomDTO) {
        ChatRoom newChatRoom = chatRoomService.createWithUsers(chatRoomDTO);

        try {
            messageSenderService.inviteToGroup(newChatRoom);
        } catch (Exception e) {
            log.error("message sender error", e.getMessage());
            e.printStackTrace();
        }

        return newChatRoom;
    }
}
