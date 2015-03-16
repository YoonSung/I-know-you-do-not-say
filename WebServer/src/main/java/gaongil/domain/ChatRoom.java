package gaongil.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_chat_room")
public class ChatRoom implements Serializable {

	private static final long serialVersionUID = -1454873402326517765L;

	@Id
	@Column(name="user_id")
	private long userId;
	
	@Id
	@Column(name="group_id")
	private long roomId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User users;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group groups;
	
	@Column(name="alarm_on")
	private boolean alarmOn;
	
	//@OneToMany(fetch=FetchType.LAZY)
	//private List<Message> messages;
	//*/
	
	public ChatRoom(){}
}
