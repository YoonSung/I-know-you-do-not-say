package gaongil.dto;

import gaongil.domain.ChatRoomSetting;
import gaongil.domain.User;

/**
 * Created by yoon on 15. 7. 1..
 */
public class ChatRoomSettingDTO {

    private User user;

    private ChatRoomDTO group;

    private boolean alarmOn;

    private ChatRoomSetting.Status status;

    public ChatRoomSetting.Status getStatus() {
        return status;
    }
}
