package gaongil.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gaongil.domain.User;
import org.springframework.util.StringUtils;

import javax.persistence.Column;

/**
 * Created by yoon on 15. 7. 1..
 */
public class UserDTO implements DTO<User> {
    private Long id;

    private String phoneNumber;

    private String nickname;

    private String imagePath;

    private String regId;

    private String uuid;

    public boolean canRegistable() {
        return isValidPhoneNumber() == true && !StringUtils.isEmpty(this.regId) ? true : false;
    }

    private boolean isValidPhoneNumber() {

        if (this.phoneNumber == null)
            return false;

        if (!this.phoneNumber.startsWith("010"))
            return false;

        if (this.phoneNumber.length() != 11)
            return false;

        return true;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public User getDomain() {
        return User.createUser(
            this.id,
            this.phoneNumber,
            this.nickname,
            this.imagePath,
            this.regId,
            this.uuid
        );
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", nickname='" + nickname + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", regId='" + regId + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
