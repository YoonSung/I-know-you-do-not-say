package gaongil.service;

import gaongil.ccs.GcmCcsSender;
import gaongil.ccs.SmackCcsClient;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoon on 15. 7. 1..
 */
@Service
public class GroupService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatRoomSettingService chatRoomSettingService;

    @Autowired
    private GcmCcsSender gcmCcsSender;

    //TODO Transaction
    public ChatRoomDTO create(User currentUser, ChatRoomDTO chatRoomDTO) {

        // Validation
        if (currentUser == null)
            throw new WithPermissionException();

        if (chatRoomDTO == null)
            throw new WrongParameterException();

        // Have to be added Users List declaration,
        // And add current login user
        List<User> addedUsers = new ArrayList<>();
        addedUsers.add(currentUser);

        // Have to be added notification
        List<User> alreadyRegisteredUser = new ArrayList<>();

        for(UserDTO userDTO : chatRoomDTO.getUsers()) {
            User selectedUser = userService.findByPhoneNumber(userDTO.getPhoneNumber());

            if (selectedUser == null) {
                selectedUser = userService.createTemporally(userDTO);
            } else {
                alreadyRegisteredUser.add(selectedUser);
            }

            addedUsers.add(selectedUser);
        }

        ChatRoom createdChatRoom = chatRoomService.createWithUsers(chatRoomDTO, addedUsers);
        ChatRoomDTO returnDTO1 = createdChatRoom.getDTO();

        for(User user : alreadyRegisteredUser) {
            ChatRoomSetting domain = chatRoomSettingService.findOne(createdChatRoom, user);
            ChatRoomSetting newDomain = ChatRoomSetting.create(domain, InvitationStatus.WAIT_USER_CONFIRM);

            ChatRoomSettingDTO returnDTO2 = chatRoomSettingService.update(newDomain).getDTO();
            returnDTO2.setUser(user);

            returnDTO1.addChatRoomSettingDTO(returnDTO2);
        }

        //TODO ccs send message to already registeredUsers

        return returnDTO1;
    }
}
