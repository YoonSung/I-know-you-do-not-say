package gaongil.service;

import gaongil.domain.ChatRoom;
import gaongil.domain.ChatRoomSetting;
import gaongil.domain.User;
import gaongil.dto.ChatRoomDTO;
import gaongil.dto.ChatRoomSettingDTO;
import gaongil.repository.ChatRoomRepository;
import gaongil.support.exception.WrongParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yoon on 15. 7. 1..
 */
@Service
public class ChatRoomService {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRoomSettingService chatRoomSettingService;

    private ChatRoom create(ChatRoomDTO dto) {
        return chatRoomRepository.save(dto.getDomain());
    }

    public ChatRoom createWithUsers(ChatRoomDTO dto, List<User> addedUsers) {
        if (dto == null ||
                !dto.canRegistable() ||
                addedUsers == null ||
                addedUsers.size() == 0)
            throw new WrongParameterException();

        //Create ChatRoom
        ChatRoom createdChatRoom = create(dto);

        //Create ChatRoomSettings
        ChatRoom newChatRoom = new ChatRoom(createdChatRoom.getId(), createdChatRoom.getName(), addedUsers);

        /*
        chatRoomSettingService.creates(createdChatRoom, addedUsers);
        userService.createsTemporally(addedUsers);

        //TODO Refactoring
        ChatRoomDTO returnDTO1 = createdChatRoom.getDTOWithReferenceData();
        */
        return chatRoomRepository.save(newChatRoom);
    }
}
