package gaongil.domain;

/**
 * Created by yoon on 15. 7. 7..
 */
public enum MessageType {
    USER_PLAIN,
    USER_WARN,
    USER_URGENT,
    USER_INFO,

    ADMIN_PLAIN,
    ADMIN_WARN,
    ADMIN_URGENT,
    ADMIN_INFO;

    static final String USER_PREFIX = "USER_";
    static final String ADMIN_PREFIX = "ADMIN_";

    public boolean isUserType() {
        if (this.name().startsWith(USER_PREFIX))
            return true;

        return false;
    }

    public boolean isAdminType() {
        if (this.name().startsWith(ADMIN_PREFIX))
            return true;

        return false;
    }
}
