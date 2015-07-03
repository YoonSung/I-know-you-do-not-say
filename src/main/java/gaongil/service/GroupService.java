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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoon on 15. 7. 1..
 */
@Service
public class GroupService {

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

        if (chatRoomDTO == null || !chatRoomDTO.canRegistable())
            throw new WrongParameterException();

        // Have to be added Users List declaration,
        // And add current login user
        List<User> newUsers = new ArrayList<>();
        newUsers.add(currentUser);

        // Have to be added notification
        List<User> alreadyRegisteredUser = new ArrayList<>();

        for(UserDTO userDTO : chatRoomDTO.getUsers()) {
            User selectedUser = userService.findByPhoneNumber(userDTO.getPhoneNumber());

            if (selectedUser == null) {
                selectedUser = userService.createTemporally(userDTO);
                newUsers.add(selectedUser);

            } else {
                alreadyRegisteredUser.add(selectedUser);
            }
        }

        //TODO insert chatRoomSettings와 chatRoom을 분리
        ChatRoom createdChatRoom = chatRoomService.createWithUsers(chatRoomDTO, newUsers);
        ChatRoomDTO returnDTO1 = createdChatRoom.getDTO();

        for(User user : alreadyRegisteredUser) {
            ChatRoomSetting domain = chatRoomSettingService.findOne(createdChatRoom, user);
            ChatRoomSetting newDomain = new ChatRoomSetting(domain.getChatRoom(), domain.getUser(), InvitationStatus.WAIT_USER_CONFIRM, domain.isAlarmOn());
            chatRoomSettingService.update(newDomain);
        }

        //TODO ccs send message to already registeredUsers

        return createdChatRoom.getDTOWithReferenceData();
    }
}
