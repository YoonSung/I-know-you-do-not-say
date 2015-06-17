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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

@Entity
@Table(name="tbl_user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String phoneNumber;
	
	@Column
	private String nickname;
	
	@Column
	private String imagePath;
	
	@Column(nullable = false)
	private String regId;

	@Column(nullable = false)
	private String uuid;

	@JsonIgnore
	@OneToOne(fetch=FetchType.LAZY, mappedBy="user")
	private Member member;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private Collection<Safezone> safezones;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private List<LocationLog> locationLogs;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="users")
	private List<ChatRoom> groups;

	public User(){}
	
	public User(String phoneNumber, String nickname, String regId) {
		this.phoneNumber = phoneNumber;
		this.nickname = nickname;
		this.regId = regId;
	}
	
	public User(long pid, String phoneNumber, String nickname, String imagePath, String regId) {
		this.id = pid;
		this.phoneNumber = phoneNumber;
		this.nickname = nickname;
		this.imagePath = imagePath;
		this.regId = regId;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber.trim().replaceAll(" ", "");;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	//Type이 정확하게 일치해야 한다. return type이 long일경우 에러발생
	public Long getPid() {
		return id;
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

	public void setRegId(String regId) {
		this.regId = regId;
	}
	
	public String getRegId() {
		return regId;
	}

	public boolean canRegistable() {
		return isValidPhoneNumber() == true && !StringUtils.isEmpty(this.regId) ? true : false;
	}

	private boolean isValidPhoneNumber() {
		if (!this.phoneNumber.startsWith("010"))
			return false;

		if (this.phoneNumber.length() != 11)
			return false;

		return true;
	}

	public String getUuid() {
		return this.uuid;
	}
}
