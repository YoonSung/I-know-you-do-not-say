package gaongil.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by yoon on 15. 6. 15..
 */
public class UserTest {

    @Test
    public void canRegistableWithInvalidPhoneNumber() {
        User user = new User();
        user.setRegId("notnull");
        user.setPhoneNumber("01099258547");
        assertThat(user.canRegistable(), is(true));

        //-가 포함된 경우
        user.setPhoneNumber("010-9925-8547");
        assertThat(user.canRegistable(), is(false));

        //010으로 시작하지 않는 경우
        user.setPhoneNumber("01199258547");
        assertThat(user.canRegistable(), is(false));

        //11자리가 아닌경우
        user.setPhoneNumber("0109925");
        assertThat(user.canRegistable(), is(false));

        user.setPhoneNumber("01099257");
        assertThat(user.canRegistable(), is(false));

        //010으로 시작하고, 나머지 8자리가 공백인 경우
        user.setPhoneNumber("010        ");
        assertThat(user.canRegistable(), is(false));
    }

}
