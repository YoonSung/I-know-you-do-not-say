package gaongil.dto;

import gaongil.domain.InvitationStatus;
import gaongil.domain.User;

/**
 * Created by yoon on 15. 7. 1..
 */
public class ChatRoomSettingDTO {

    private UserDTO user;

    private boolean alarmOn;

    private InvitationStatus status;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "ChatRoomSettingDTO{" +
                "user=" + user +
                ", alarmOn=" + alarmOn +
                ", status=" + status +
                '}';
    }
}
