package gaongil.service;

import gaongil.domain.ChatRoom;
import gaongil.domain.ChatRoomSetting;
import gaongil.domain.ChatRoomSettingPK;
import gaongil.domain.User;
import gaongil.repository.ChatRoomSettingRepository;
import gaongil.support.exception.WrongParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yoon on 15. 7. 2..
 */
@Service
public class ChatRoomSettingService {

    @Autowired
    ChatRoomSettingRepository chatRoomSettingRepository;

    public ChatRoomSetting findOne(ChatRoom chatRoom, User user) {
        if (chatRoom == null
                || chatRoom.getId() == null
                || user == null
                || user.getId() == null)
            throw new WrongParameterException();

        return chatRoomSettingRepository.findOne(new ChatRoomSettingPK(chatRoom.getId(), user.getId()));
    }

    public ChatRoomSetting update(ChatRoomSetting newDomain) {
        if (newDomain == null)
            throw new WrongParameterException();

        return chatRoomSettingRepository.save(newDomain);
    }
}
