package gaongil.domain;

/**
 * Created by yoon on 15. 7. 2..
 */

import java.io.Serializable;

public class ChatRoomSettingPK implements Serializable {
/*
    private Long user;
    private Long chatRoom;

    public ChatRoomSettingPK() {
    }

    public ChatRoomSettingPK(Long userId, Long chatRoomId) {
        this.user = userId;
        this.chatRoom = chatRoomId;
    }

    public ChatRoomSettingPK(ChatRoom chatRoom, User user) {
        this.chatRoom = chatRoom.getId();
        this.user = user.getId();
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

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(Long chatRoom) {
        this.chatRoom = chatRoom;
    }
*/
    /*
    private Long userId;
    private Long chatRoomId;

    public ChatRoomSettingPK() {
    }

    public ChatRoomSettingPK(Long userId, Long chatRoomId) {
        this.userId = userId;
        this.chatRoomId = chatRoomId;
    }

    public ChatRoomSettingPK(ChatRoom chatRoom, User user) {
        this.chatRoomId = chatRoom.getId();
        this.userId = user.getId();
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
*/

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

    public User getUser() {
        return user;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
