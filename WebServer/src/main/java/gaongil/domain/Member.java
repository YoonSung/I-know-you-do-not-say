package gaongil.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tbl_member")
public class Member {

	enum SEX {
		M,
		W
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	private String email;
	
	@Column
	private String password;
	
	@Column
	private Short age;
	
	@Column
	private SEX sex;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createdDate;
	
	@JsonIgnore
	@OneToOne
	//@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", email=" + email + ", password=" + password + ", age=" + age + ", sex=" + sex + ", createdDate=" + createdDate
				+ ", user=" + user + "]";
	}
}
