package gaongil.domain;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yoon on 15. 7. 7..
 */
public class MessageTest {
    @Test
    public void 유저_관리자_타입구분_테스트() {
        MessageType type = MessageType.ADMIN_INFO;

        assertFalse(type.isUserType());
        assertTrue(type.isAdminType());
    }
}