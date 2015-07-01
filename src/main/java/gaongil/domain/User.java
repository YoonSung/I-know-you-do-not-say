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
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.util.StringUtils;

//TODO Seperate to DTO, domain
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

	public static User createUser(Long id, String phoneNumber, String nickname, String imagePath, String regId, String uuid) {
		return new User(id, phoneNumber, nickname, imagePath, regId, uuid);
	}

	private User(Long id, String phoneNumber, String nickname, String imagePath, String regId, String uuid) {
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.nickname = nickname;
		this.imagePath = imagePath;
		this.regId = regId;
		this.uuid = uuid;
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

	public String getUuid() {
		return this.uuid;
	}

	//Type이 정확하게 일치해야 한다. return type이 long일경우 에러발생
	public Long getId() {
		return id;
	}
}
