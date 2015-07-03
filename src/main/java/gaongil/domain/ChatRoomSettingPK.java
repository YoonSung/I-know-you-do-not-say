package gaongil.domain;

/**
 * Created by yoon on 15. 7. 2..
 */

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class ChatRoomSettingPK implements Serializable  {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_room_id", nullable = false, insertable = false, updatable = false)
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

    public User getUser() {
        return user;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

}
