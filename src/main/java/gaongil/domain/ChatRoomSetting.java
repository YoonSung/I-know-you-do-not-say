package gaongil.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="tbl_chat_room_setting")
public class ChatRoomSetting {

	public enum Status {
		WAIT_USER_CONFIRM,
		NOT_REGISTRATION,
		JOIN,
		//BANISH,
	}

	@Id
	@Column(name="user_id")
	private Long userId;
	
	@Id
	@Column(name="group_id")
	private long roomId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private ChatRoom group;
	
	@Column(name="alarm_on")
	private boolean alarmOn;

	@Enumerated(EnumType.STRING)
	private Status status;

	public ChatRoomSetting(){}
}
