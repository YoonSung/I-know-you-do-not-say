package gaongil.domain;

import gaongil.dto.ChatRoomDTO;
import gaongil.dto.ChatRoomSettingDTO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="tbl_chat_room")
public class ChatRoom implements ConvertableToDto<ChatRoomDTO> {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinTable(name="tbl_chat_room_setting", joinColumns=@JoinColumn(name="chat_room_id"), inverseJoinColumns=@JoinColumn(name="user_id"))
	private List<User> users;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "to")
	private List<Message> messages;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id.chatRoom")
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

	@Override
	public ChatRoomDTO toDTOExcludeReferenceData() {
		ChatRoomDTO dto = new ChatRoomDTO();
		dto.setId(this.id);
		dto.setName(this.name);
		//dto.setCreatedDate(this.createdDate);

		return dto;
	}

	public Long getId() {
		return id;
	}

	@Override
	public ChatRoomDTO toDTOWithReferenceData() {
		ChatRoomDTO dto1 = toDTOExcludeReferenceData();
		List<ChatRoomSetting> chatRoomSettings = getChatRoomSettings();
		if (chatRoomSettings == null) {
			return dto1;

		} else {
			List<ChatRoomSettingDTO> dto2 = new ArrayList<>();
			for (ChatRoomSetting chatRoomSetting : chatRoomSettings) {
				dto2.add(chatRoomSetting.toDTOWithReferenceData());
			}

			dto1.setChatRoomSettings(dto2);
		}

		return dto1;
	}

	public String getName() {
		return name;
	}

	public List<ChatRoomSetting> getChatRoomSettings() {
		return chatRoomSettings;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
