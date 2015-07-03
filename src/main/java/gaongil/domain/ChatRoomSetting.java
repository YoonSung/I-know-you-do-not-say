package gaongil.domain;

import gaongil.dto.ChatRoomDTO;
import gaongil.dto.ChatRoomSettingDTO;
import gaongil.dto.UserDTO;

import javax.persistence.*;

@Entity
@Table(name="tbl_chat_room_setting")
public class ChatRoomSetting {

	@AttributeOverrides({
			@AttributeOverride(name = "chatRoomId", column = @Column(name="chat_room_id")),
			@AttributeOverride(name="userId", column = @Column(name="user_id"))
	})
	@EmbeddedId
	ChatRoomSettingPK id;

	//@Id
	//@MapsId("chatRoomId")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "chat_room_id", nullable = false, insertable = false, updatable = false)
	//@JoinColumn(name="chat_room_id")
	//@MapsId("chat_room_id")
	private ChatRoom chatRoom;

	//@Id
	//@MapsId("userId")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	//@JoinColumn(name="user_id")
//	@MapsId("user_id")
	private User user;

	//TODO H2database not yet support, 1.5 Roadmap will support enum type
	//@Column(name="status", columnDefinition = InvitationStatus.COLUMN_DEFINITION)
	@Column(name="status", columnDefinition = "varchar(20) default 'NOT_REGISTRATION'")
	@Enumerated(EnumType.STRING)
	private InvitationStatus status;

	@Column(name="alarm_on")
	private boolean alarmOn;

	public ChatRoomSetting(){}

	public ChatRoomSetting(ChatRoomSettingPK chatRoomSettingPK, InvitationStatus status) {
		this.id = chatRoomSettingPK;
		this.status = status;
	}

	public ChatRoomSettingDTO getDTO() {
		ChatRoomSettingDTO dto = new ChatRoomSettingDTO();
		dto.setAlarmOn(alarmOn);
		dto.setStatus(status);

		return dto;
	}

	public ChatRoomSettingDTO getDTOWithReferenceData() {

		ChatRoomSettingDTO dto  = new ChatRoomSettingDTO();
		dto.setStatus(this.status);
		dto.setUser(this.user.getDTO());

		return dto;
	}

	public User getUser() {
		return user;
	}

	public ChatRoom getChatRoom() {
		return chatRoom;
	}

	public boolean isAlarmOn() {
		return alarmOn;
	}

	public InvitationStatus getStatus() {
		return status;
	}

	public ChatRoomSettingPK getId() {
		return id;
	}

	public void setId(ChatRoomSettingPK id) {
		this.id = id;
	}

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setStatus(InvitationStatus status) {
		this.status = status;
	}

	public void setAlarmOn(boolean alarmOn) {
		this.alarmOn = alarmOn;
	}
}
