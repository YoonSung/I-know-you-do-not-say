package gaongil.domain;

import gaongil.dto.ChatRoomDTO;
import gaongil.dto.DTO;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

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

	public ChatRoom() {}

	private ChatRoom(String name) {
		this.name = name;
	}

	private ChatRoom(String name, List<User> users) {
		this.name = name;
		this.users = users;
	}

	public static ChatRoom create(String name) {
		return new ChatRoom(name);
	}

	public static ChatRoom create(String name, List<User> addedUsers) {
		return new ChatRoom(name, addedUsers);
	}

	public ChatRoomDTO getDTO() {
		ChatRoomDTO dto = new ChatRoomDTO();
		dto.setId(this.id);
		dto.setName(this.name);
		dto.setCreatedDate(this.createdDate);

		return dto;
	}

	public Long getId() {
		return id;
	}
}
