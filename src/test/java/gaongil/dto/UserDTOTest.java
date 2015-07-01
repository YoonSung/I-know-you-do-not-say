package gaongil.dto;

import gaongil.domain.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by yoon on 15. 6. 15..
 */
public class UserDTOTest {

    @Test
    public void canRegistableWithInvalidPhoneNumber() {
        UserDTO userDTO = new UserDTO();
        userDTO.setRegId("notnull");
        userDTO.setPhoneNumber("01099258547");
        assertThat(userDTO.canRegistable(), is(true));

        //-가 포함된 경우
        userDTO.setPhoneNumber("010-9925-8547");
        assertThat(userDTO.canRegistable(), is(false));

        //010으로 시작하지 않는 경우
        userDTO.setPhoneNumber("01199258547");
        assertThat(userDTO.canRegistable(), is(false));

        //11자리가 아닌경우
        userDTO.setPhoneNumber("0109925");
        assertThat(userDTO.canRegistable(), is(false));

        userDTO.setPhoneNumber("01099257");
        assertThat(userDTO.canRegistable(), is(false));

        //010으로 시작하고, 나머지 8자리가 공백인 경우
        userDTO.setPhoneNumber("010        ");
        assertThat(userDTO.canRegistable(), is(false));
    }

}
