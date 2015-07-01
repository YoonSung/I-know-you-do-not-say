package gaongil.dto;

import gaongil.domain.ChatRoom;
import gaongil.domain.Message;
import gaongil.domain.User;

import javax.persistence.*;
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

	@Override
	public ChatRoom getDomain() {
		return null;
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
}
