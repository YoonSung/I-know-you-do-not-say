package gaongil.dto;

import gaongil.domain.InvitationStatus;
import gaongil.domain.User;

/**
 * Created by yoon on 15. 7. 1..
 */
public class ChatRoomSettingDTO {

    private User user;

    private ChatRoomDTO group;

    private boolean alarmOn;

    private InvitationStatus status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChatRoomDTO getGroup() {
        return group;
    }

    public void setGroup(ChatRoomDTO group) {
        this.group = group;
    }

    public boolean isAlarmOn() {
        return alarmOn;
    }

    public void setAlarmOn(boolean alarmOn) {
        this.alarmOn = alarmOn;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    public InvitationStatus getStatus() {
        return status;
    }
}
