package gaongil.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long pid;
	
	@Column
	private String phoneNumber;
	
	@Column
	private String nickname;
	
	@Column
	private String imagePath;
	
	@Column(nullable=false)
	private String regId;
	
	@OneToOne(fetch=FetchType.LAZY)
	private Member member;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private Collection<Safezone> safezones;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private List<LocationLog> locationLogs;
	
//	/*
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="users")
	private List<Group> groups;
//	 */
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="users")
	private List<ChatRoom> chatRooms;
	
	public User(){}
	
	public User(String phoneNumber, String nickname, String regId) {
		this.phoneNumber = phoneNumber;
		this.nickname = nickname;
		this.regId = regId;
	}
	
	public User(long pid, String phoneNumber, String nickname, String imagePath, String regId) {
		this.pid = pid;
		this.phoneNumber = phoneNumber;
		this.nickname = nickname;
		this.imagePath = imagePath;
		this.regId = regId;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	//Type이 정확하게 일치해야 한다. return type이 long일경우 에러발생
	public Long getPid() {
		return pid;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getNickname() {
		return nickname;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getRegId() {
		return regId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
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
		return "User [pid=" + pid + ", phoneNumber=" + phoneNumber + ", nickname=" + nickname + ", imagePath=" + imagePath
				+ ", regId=" + regId + "]";
	}
}
