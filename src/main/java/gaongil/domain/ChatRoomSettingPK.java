package gaongil.domain;

/**
 * Created by yoon on 15. 7. 2..
 */

import java.io.Serializable;

public class ChatRoomSettingPK implements Serializable {

    private long userId;
    private long groupId;

    public ChatRoomSettingPK(long userId, long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public boolean equals(Object object) {
        if (object instanceof ChatRoomSettingPK) {
            ChatRoomSettingPK pk = (ChatRoomSettingPK)object;
            return userId == pk.userId && groupId == pk.groupId;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (int)(userId + groupId);
    }
}
