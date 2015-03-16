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
	private Long id;
	
	@Column
	private String phoneNumber;
	
	@Column
	private String nickname;
	
	@Column
	private String imagePath;
	
	@Column(nullable=false)
	private String regId;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="user")
	private Member member;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private Collection<Safezone> safezones;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private List<LocationLog> locationLogs;
	
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
		this.phoneNumber = phoneNumber;
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

	public String getRegId() {
		return regId;
	}
}
