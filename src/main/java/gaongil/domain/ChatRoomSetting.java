package gaongil.domain;

import gaongil.dto.ChatRoomDTO;
import gaongil.dto.ChatRoomSettingDTO;
import gaongil.dto.UserDTO;

import javax.persistence.*;

@Entity
@Table(name="tbl_chat_room_setting")
@IdClass(ChatRoomSettingPK.class)
public class ChatRoomSetting {

	@Id
	@ManyToOne
	@JoinColumn(name="chat_room_id")
	private ChatRoom chatRoom;

	@Id
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	//TODO H2database not yet support, 1.5 Roadmap will support enum type
	//@Column(name="status", columnDefinition = InvitationStatus.COLUMN_DEFINITION)
	@Column(name="status", columnDefinition = "varchar(20) default 'NOT_REGISTRATION'")
	@Enumerated(EnumType.STRING)
	private InvitationStatus status;

	@Column(name="alarm_on")
	private boolean alarmOn;

	public ChatRoomSetting(){}

	public ChatRoomSetting(ChatRoom chatRoom, User user, InvitationStatus status, boolean alarmOn) {
		this.chatRoom = chatRoom;
		this.user = user;
		this.status = status;
		this.alarmOn = alarmOn;
	}

	public ChatRoomSetting(ChatRoom chatRoom, User user, InvitationStatus status) {
		this.chatRoom = chatRoom;
		this.user = user;
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
}
