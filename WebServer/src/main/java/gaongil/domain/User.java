package gaongil.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long pid;
	
	@Column
	private String phoneNumber;
	
	@Column
	private boolean isMember;
	
	@Column
	private String nickname;
	
	@Column
	private String imagePath;
	
	@Column(nullable=false)
	private String regId;
	
	public User(){}
	
	public User(String phoneNumber, String nickname, String regId) {
		this.phoneNumber = phoneNumber;
		this.nickname = nickname;
		this.regId = regId;
	}
	
	public User(long pid, String phoneNumber, boolean isMember, String nickname, String imagePath, String regId) {
		this.pid = pid;
		this.phoneNumber = phoneNumber;
		this.isMember = isMember;
		this.nickname = nickname;
		this.imagePath = imagePath;
		this.regId = regId;
	}

	//Type이 정확하게 일치해야 한다. return type이 long일경우 에러발생
	public Long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public boolean isMember() {
		return isMember;
	}
	public void setMember(boolean isMember) {
		this.isMember = isMember;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + (isMember ? 1231 : 1237);
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		result = prime * result + ((regId == null) ? 0 : regId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		if (isMember != other.isMember)
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		if (regId == null) {
			if (other.regId != null)
				return false;
		} else if (!regId.equals(other.regId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [pid=" + pid + ", phoneNumber=" + phoneNumber + ", isMember=" + isMember + ", nickname=" + nickname + ", imagePath=" + imagePath
				+ ", regId=" + regId + "]";
	}
}
