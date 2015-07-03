package gaongil.domain;

/**
 * Created by yoon on 15. 7. 2..
 */

import java.io.Serializable;

public class ChatRoomSettingPK implements Serializable {

    private User user;
    private ChatRoom chatRoom;

    public ChatRoomSettingPK(){}

    public ChatRoomSettingPK(ChatRoom chatRoom, User user) {
        this.user =  user;
        this.chatRoom = chatRoom;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ChatRoomSettingPK) {
            ChatRoomSettingPK pk = (ChatRoomSettingPK) object;
            return user == pk.user && chatRoom == pk.chatRoom;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (chatRoom != null ? chatRoom.hashCode() : 0);
        return result;
    }
}
