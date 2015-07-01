package gaongil.dto;

import gaongil.domain.ChatRoom;
import gaongil.domain.Message;
import gaongil.domain.User;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatRoomDTO implements DTO<ChatRoom> {

	private Long id;
	
	private String name;

	private List<ChatRoomSettingDTO> chatRoomSettings;

	private List<UserDTO> users;
	
	private List<Message> messages;
	
	private Date createdDate;

	public ChatRoomDTO(String name) {
		this.name = name;
	}

	public ChatRoomDTO() {

	}

	public boolean canRegistable() {
		if (StringUtils.isEmpty(this.name.trim()))
			return false;

		if (this.users.size() == 0)
			return false;

		return true;
	}

	@Override
	public ChatRoom getDomain() {
		return ChatRoom.create(
				this.name
		);
	}

	public ChatRoom getDomainWithUsers(List<User> addedUsers) {
		return ChatRoom.create(
				this.name,
				addedUsers
		);
	}

	public void addChatRoomSettingDTO(ChatRoomSettingDTO dto) {
		if (this.chatRoomSettings == null)
			chatRoomSettings = new ArrayList<>();

		chatRoomSettings.add(dto);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserDTO> getUserDTOs() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<ChatRoomSettingDTO> getChatRoomSettings() {
		return chatRoomSettings;
	}

	public List<UserDTO> getUsers() {
		return users;
	}
}
