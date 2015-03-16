package gaongil.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbl_chat_room")
public class ChatRoom {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@ManyToMany
	@JoinTable(name="tbl_chat_room_setting", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="group_id"))
	private List<User> users;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="chat_room_id")
	private List<Message> messages;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createdDate;
}
