package gaongil.safereturnhome.model;

public class UserStatus {
	
	private String name;
	private String iconPath;
	
	public UserStatus(String name, String iconPath) {
		super();
		this.name = name;
		this.iconPath = iconPath;
	}

	public String getName() {
		return name;
	}

	public String getIconPath() {
		return iconPath;
	}
}
