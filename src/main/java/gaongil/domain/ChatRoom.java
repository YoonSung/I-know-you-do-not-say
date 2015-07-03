package gaongil.domain;

import gaongil.dto.ChatRoomDTO;
import gaongil.dto.ChatRoomSettingDTO;
import gaongil.dto.DTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinTable(name="tbl_chat_room_setting", joinColumns=@JoinColumn(name="chat_room_id"), inverseJoinColumns=@JoinColumn(name="user_id"))
	private List<User> users;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name="chat_room_id")
	private List<Message> messages;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "chat_room_id")
	private List<ChatRoomSetting> chatRoomSettings;

	//TODO
	//@Temporal(TemporalType.TIMESTAMP)
	//@Column(nullable = false, updatable = false)
	//@Transient
	//private Date createdDate;

	public ChatRoom() {}

	public ChatRoom(Long id, String name, List<User> users) {
		this.id = id;
		this.name = name;
		this.users = users;
	}

	public ChatRoom(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public ChatRoomDTO getDTO() {
		ChatRoomDTO dto = new ChatRoomDTO();
		dto.setId(this.id);
		dto.setName(this.name);
		//dto.setCreatedDate(this.createdDate);

		return dto;
	}

	public Long getId() {
		return id;
	}

	public ChatRoomDTO getDTOWithReferenceData() {
		ChatRoomDTO dto1 = getDTO();
		List<ChatRoomSettingDTO> dto2 = new ArrayList<>();

		for (ChatRoomSetting chatRoomSetting : this.chatRoomSettings) {
			dto2.add(chatRoomSetting.getDTOWithReferenceData());
		}

		return null;
	}

	public String getName() {
		return name;
	}
}
