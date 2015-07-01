package acceptance_test.controller;

import acceptance_test.controller.common.WithIntergrationTest;
import acceptance_test.controller.common.WithTokenRule;
import com.jayway.restassured.response.Response;
import gaongil.domain.ChatRoomSetting;
import gaongil.dto.ChatRoomDTO;
import gaongil.dto.ChatRoomSettingDTO;
import gaongil.dto.UserDTO;
import gaongil.support.web.converter.ResponseMessage;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by yoon on 15. 7. 1..
 */
public class ChatRoomControllerTest extends WithIntergrationTest {

    @Test
    public void 그룹생성() {

        String chatRoomTitle = "테스트그룹";
        String phoneNumberPrefix = "01099258547";
        String nicknamePrefix = "테스트친구_";
        int userNumber = 3;

        //GIVEN
        List<UserDTO> invitedUsers = new ArrayList<>();

        for (int i = 0; i < userNumber; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setPhoneNumber(phoneNumberPrefix+i);
            userDTO.setNickname(nicknamePrefix+i);
        }

        ChatRoomDTO chatRoomDTO = new ChatRoomDTO(chatRoomTitle);
        chatRoomDTO.setUsers(invitedUsers);

        Response response = given(WithTokenRule.TYPE.USER).
                body(invitedUsers).

        //WHEN, THEN
        when().
                post("/groups").
        then().extract().response();

        //CHECK RESULT

        ResponseMessage responseMessage = response.as(ResponseMessage.class);

        //1. check status code
        assertEquals(201, responseMessage.getCode());

        //2. check chatRoomTitle
        ChatRoomDTO resultChatRoomDTO = (ChatRoomDTO) responseMessage.getData();
        assertEquals(chatRoomTitle, resultChatRoomDTO.getName());

        List<UserDTO> resultUsers = resultChatRoomDTO.getUserDTOs();
        //3. check Group size
        assertEquals(3, resultUsers.size());

        //4. check Group user nicknames
        ArrayList<String> nickNames = new ArrayList<>();
        for (UserDTO userDTO : resultUsers) {
            nickNames.add(userDTO.getNickname());
        }

        for (int i = 0; i < userNumber; i++) {
            assertTrue(nickNames.contains(nicknamePrefix + i));
        }

        //5. check Group user status
        List<ChatRoomSettingDTO> resultChatRoomSettingDTOs = resultChatRoomDTO.getChatRoomSettings();
        for (ChatRoomSettingDTO dto : resultChatRoomSettingDTOs) {
            assertEquals(dto.getStatus(), ChatRoomSetting.Status.NOT_REGISTRATION);
        }
    }
}
