package gaongil.domain;

/**
 * Created by yoon on 15. 7. 2..
 */

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ChatRoomSettingPK implements Serializable  {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "chat_room_id")
    private Long chatRoomId;

    public ChatRoomSettingPK() {
    }

    public ChatRoomSettingPK(ChatRoom chatRoomId, User userId) {
        this.chatRoomId = chatRoomId.getId();
        this.userId = userId.getId();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ChatRoomSettingPK) {
            ChatRoomSettingPK pk = (ChatRoomSettingPK) object;
            return userId == pk.userId && chatRoomId == pk.chatRoomId;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (chatRoomId != null ? chatRoomId.hashCode() : 0);
        return result;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
    /*
    private Long userId;
    private Long chatRoomId;

    public ChatRoomSettingPK() {
    }

    public ChatRoomSettingPK(Long userId, Long chatRoomId) {
        this.userId = userId;
        this.chatRoomId = chatRoomId;
    }

    public ChatRoomSettingPK(ChatRoom chatRoomId, User userId) {
        this.chatRoomId = chatRoomId.getId();
        this.userId = userId.getId();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ChatRoomSettingPK) {
            ChatRoomSettingPK pk = (ChatRoomSettingPK) object;
            return userId == pk.userId && chatRoomId == pk.chatRoomId;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (chatRoomId != null ? chatRoomId.hashCode() : 0);
        return result;
    }


    private User userId;
    private ChatRoom chatRoomId;

    public ChatRoomSettingPK(){}

    public ChatRoomSettingPK(ChatRoom chatRoomId, User userId) {
        this.userId =  userId;
        this.chatRoomId = chatRoomId;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ChatRoomSettingPK) {
            ChatRoomSettingPK pk = (ChatRoomSettingPK) object;
            return userId == pk.userId && chatRoomId == pk.chatRoomId;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (chatRoomId != null ? chatRoomId.hashCode() : 0);
        return result;
    }

    public User getUserId() {
        return userId;
    }

    public ChatRoom getChatRoomId() {
        return chatRoomId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public void setChatRoomId(ChatRoom chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
    */
}
