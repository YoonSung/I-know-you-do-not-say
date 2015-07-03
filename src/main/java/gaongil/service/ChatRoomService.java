package gaongil.service;

import gaongil.domain.*;
import gaongil.dto.ChatRoomDTO;
import gaongil.dto.ChatRoomSettingDTO;
import gaongil.dto.UserDTO;
import gaongil.repository.ChatRoomRepository;
import gaongil.support.exception.WrongParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoon on 15. 7. 1..
 */
@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatRoomSettingService chatRoomSettingService;

    private ChatRoom create(ChatRoomDTO dto) {
        return chatRoomRepository.save(dto.getDomain());
    }

    public ChatRoom createWithUsers(ChatRoomDTO dto) {
        ChatRoom newChatRoom = createWithUsersIncludeCurrentUser(dto);
        return chatRoomRepository.findOne(newChatRoom.getId());
    }

    @Transactional
    private ChatRoom createWithUsersIncludeCurrentUser(ChatRoomDTO dto) {
        if (dto == null ||!dto.canRegistable())
            throw new WrongParameterException();

        // Create ChatRoom
        ChatRoom createdChatRoom = create(dto);

        // Create currentUser's ChatRoomSetting
        chatRoomSettingService.create(new ChatRoomSetting(new ChatRoomSettingPK(createdChatRoom, userService.getCurrentLoginUser()), InvitationStatus.JOIN));

        // TODO : seperate to method
        // Create newUser and ChatRoomSetting
        for(UserDTO userDTO : dto.getUsers()) {
            User selectedUser = userService.findByPhoneNumber(userDTO.getPhoneNumber());

            InvitationStatus status = InvitationStatus.NOT_REGISTRATION;

            if (selectedUser == null) {
                selectedUser = userService.createTemporally(userDTO);
            } else {
                status = InvitationStatus.WAIT_USER_CONFIRM;
            }

            ChatRoomSetting chatRoomSetting = new ChatRoomSetting(new ChatRoomSettingPK(createdChatRoom, selectedUser), status);
            chatRoomSettingService.create(chatRoomSetting);
        }

        return createdChatRoom;
    }
}
