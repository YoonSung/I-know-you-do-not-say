package gaongil.domain;

/**
 * Created by yoon on 15. 7. 2..
 */
public enum InvitationStatus {
    WAIT_USER_CONFIRM,
    NOT_REGISTRATION,
    JOIN;
    //BANISH,

    public static final String COLUMN_DEFINITION = "enum ('WAIT_USER_CONFIRM', 'NOT_REGISTRATION', 'JOIN') default 'NOT_REGISTRATION'";
}
