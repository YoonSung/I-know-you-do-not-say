package gaongil.domain;

public class User {
	private int pid;
	private String phoneNumber;
	private boolean isMember;
	private String nickname;
	private String imagePath;
	private String registrationId;
	
	public User(String phoneNumber, String nickname, String registrationId) {
		this.phoneNumber = phoneNumber;
		this.nickname = nickname;
		this.registrationId = registrationId;
	}
	
	public User(int pid, String phoneNumber, boolean isMember, String nickname, String imagePath, String registrationId) {
		super();
		this.pid = pid;
		this.phoneNumber = phoneNumber;
		this.isMember = isMember;
		this.nickname = nickname;
		this.imagePath = imagePath;
		this.registrationId = registrationId;
	}

	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
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
	public String getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + (isMember ? 1231 : 1237);
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + pid;
		result = prime * result + ((registrationId == null) ? 0 : registrationId.hashCode());
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
		if (pid != other.pid)
			return false;
		if (registrationId == null) {
			if (other.registrationId != null)
				return false;
		} else if (!registrationId.equals(other.registrationId))
			return false;
		return true;
	}
}
