package gaongil.domain;

import gaongil.dto.ChatRoomSettingDTO;

import javax.persistence.*;

@Entity
@Table(name="tbl_chat_room_setting")
@IdClass(ChatRoomSettingPK.class)
public class ChatRoomSetting {

	@Id
	@Column(name="user_id")
	private Long userId;

	@Id
	@Column(name="group_id")
	private Long roomId;

	//@ManyToOne
	//@JoinColumn(name="user_id")
	@Transient
	private User user;
	
	//@ManyToOne
	//@JoinColumn(name="group_id")
	@Transient
	private ChatRoom group;

	@Column(name="alarm_on")
	private boolean alarmOn;

	@Column(name="status", columnDefinition = InvitationStatus.COLUMN_DEFINITION)
	@Enumerated(EnumType.STRING)
	private InvitationStatus status;

	public ChatRoomSetting(){}

	private ChatRoomSetting(ChatRoomSetting domain, InvitationStatus status) {
		this.userId = domain.getUserId();
		this.roomId = domain.getRoomId();
		this.user = domain.getUser();
		this.group = domain.getGroup();
		this.alarmOn = domain.isAlarmOn();
		this.status = status;
	}

	public ChatRoomSettingDTO getDTO() {
		ChatRoomSettingDTO dto = new ChatRoomSettingDTO();
		dto.setUser(user);
		dto.setGroup(group.getDTO());
		dto.setAlarmOn(alarmOn);
		dto.setStatus(status);

		return dto;
	}

	public static ChatRoomSetting create(ChatRoomSetting domain, InvitationStatus waitUserConfirm) {
		return new ChatRoomSetting(domain, waitUserConfirm);
	}

	public Long getUserId() {
		return userId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public User getUser() {
		return user;
	}

	public ChatRoom getGroup() {
		return group;
	}

	public boolean isAlarmOn() {
		return alarmOn;
	}

	public InvitationStatus getStatus() {
		return status;
	}
}
