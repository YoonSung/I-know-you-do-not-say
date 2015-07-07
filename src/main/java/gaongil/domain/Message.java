package gaongil.domain;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="tbl_message")
public class Message {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(nullable=false)
	private String message;

	//TODO H2database not yet support, 1.5 Roadmap will support enum type
	@Column(nullable = false, columnDefinition = "varchar(15) default 'test'")
	@Enumerated(EnumType.STRING)
	private MessageType type;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name="chat_room_id", nullable = false, updatable = false)
	private ChatRoom to;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false, updatable=false)
	private Date createdTime;

	public Message(){}

	private Message(User from, ChatRoom to, MessageType messageType, String message) {
		// Validation
		Assert.notNull(from);
		Assert.notNull(to);
		Assert.notNull(messageType);
		Assert.hasLength(message);
		Assert.hasLength(from.getPhoneNumber());

		// Save data
		this.user = from;
		this.to = to;
		this.type = messageType;
		this.message = message;
	}

	private Message(ChatRoom to, MessageType messageType, String message) {
		// Validation
		Assert.notNull(to);
		Assert.notNull(messageType);
		Assert.hasLength(message);

		// Save data
		this.to = to;
		this.type = messageType;
		this.message = message;
	}

	//TODO message validation
	public static Message createUserMessage(User from, ChatRoom to, MessageType messageType, String message) {
		if (messageType.isAdminType())
			throw new IllegalArgumentException();

		return new Message(from, to, messageType, message);
	}

	public static Message createAdminMessage(ChatRoom to, MessageType messageType, String message) {
		if (messageType.isUserType())
			throw new IllegalArgumentException();

		return new Message(to, messageType, message);
	}
}
