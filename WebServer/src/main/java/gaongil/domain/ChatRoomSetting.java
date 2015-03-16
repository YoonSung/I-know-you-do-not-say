package gaongil.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_chat_room_setting")
public class ChatRoomSetting implements Serializable {

	private static final long serialVersionUID = -1454873402326517765L;

	@Id
	@Column(name="user_id")
	private Long userId;
	
	@Id
	@Column(name="group_id")
	private long roomId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User users;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private ChatRoom groups;
	
	@Column(name="alarm_on")
	private boolean alarmOn;
	
	public ChatRoomSetting(){}
}
