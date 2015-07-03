package gaongil.service;

import gaongil.ccs.GcmCcsSender;
import gaongil.domain.ChatRoom;
import gaongil.domain.ChatRoomSetting;
import gaongil.domain.InvitationStatus;
import gaongil.domain.User;
import gaongil.dto.ChatRoomDTO;
import gaongil.dto.ChatRoomSettingDTO;
import gaongil.dto.UserDTO;
import gaongil.repository.ChatRoomRepository;
import gaongil.support.exception.WithPermissionException;
import gaongil.support.exception.WrongParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoon on 15. 7. 1..
 */
@Service
public class GroupService {

    @Autowired
    private ChatRoomService chatRoomService;

    //TODO Transaction
    public ChatRoom create(User currentUser, ChatRoomDTO chatRoomDTO) {

        // Validation
        if (currentUser == null)
            throw new WithPermissionException();

        if (chatRoomDTO == null || !chatRoomDTO.canRegistable())
            throw new WrongParameterException();

        return chatRoomService.createWithUsers(chatRoomDTO);
    }
}
