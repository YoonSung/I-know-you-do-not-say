package gaongil.service;

import gaongil.domain.ChatRoom;
import gaongil.dto.ChatRoomDTO;
import gaongil.support.exception.WrongParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yoon on 15. 7. 1..
 */
@Service
public class GroupService {

    @Autowired
    private ChatRoomService chatRoomService;

    //TODO Transaction
    public ChatRoom create(ChatRoomDTO chatRoomDTO) {

        // Validation
        if (chatRoomDTO == null || !chatRoomDTO.canRegistable())
            throw new WrongParameterException();

        return chatRoomService.createWithUsers(chatRoomDTO);
    }
}
