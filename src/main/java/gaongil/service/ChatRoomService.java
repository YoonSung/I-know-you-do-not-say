package gaongil.service;

import gaongil.domain.ChatRoom;
import gaongil.domain.ChatRoomSetting;
import gaongil.domain.User;
import gaongil.dto.ChatRoomDTO;
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
    private ChatRoomRepository chatRoomRepository;

    public ChatRoom createWithUsers(ChatRoomDTO dto, List<User> addedUsers) {
        if (dto == null ||
                !dto.canRegistable() ||
                addedUsers == null ||
                addedUsers.size() == 0)
            throw new WrongParameterException();

        return chatRoomRepository.save(dto.getDomainWithUsers(addedUsers));
    }
}
