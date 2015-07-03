package acceptance_test.controller;

import acceptance_test.controller.common.WithIntergrationTest;
import acceptance_test.controller.common.WithTokenRule;
import com.jayway.restassured.response.Response;
import gaongil.domain.ChatRoomSetting;
import gaongil.domain.InvitationStatus;
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
public class GroupControllerTest extends WithIntergrationTest {

    @Test
    public void 생성() {

        final String chatRoomTitle = "테스트그룹";
        final String phoneNumberPrefix = "0109925854";
        final String nicknamePrefix = "테스트친구_";
        final int userNumber = 3;

        // GIVEN
        List<UserDTO> invitedUsers = new ArrayList<>();

        for (int i = 0; i < userNumber; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setPhoneNumber(phoneNumberPrefix+i);
            userDTO.setNickname(nicknamePrefix+i);
            invitedUsers.add(userDTO);
        }

        ChatRoomDTO chatRoomDTO = new ChatRoomDTO(chatRoomTitle);
        chatRoomDTO.setUsers(invitedUsers);

        Response response = given(WithTokenRule.TYPE.USER).
                body(chatRoomDTO).

        // WHEN, THEN
        when().
                post("/groups").
        then().extract().response();

        // CHECK RESULT
        ResponseMessage responseMessage = response.as(ResponseMessage.class);

        // 1. check status code
        assertEquals(201, responseMessage.getCode());

        // 2. check chatRoomTitle
        ChatRoomDTO resultChatRoomDTO = casting(responseMessage.getData(), ChatRoomDTO.class);
        assertEquals(chatRoomTitle, resultChatRoomDTO.getName());

        List<ChatRoomSettingDTO> resultChatRoomSettings = resultChatRoomDTO.getChatRoomSettings();

        // 3. check Group size
        // current user +1
        assertEquals(userNumber + 1, resultChatRoomSettings.size());

        // 4. check Group user nicknames
        ArrayList<String> nickNames = new ArrayList<>();

        for (ChatRoomSettingDTO chatRoomSettingDTO : resultChatRoomSettings) {
            nickNames.add(chatRoomSettingDTO.getUser().getNickname());
        }

        for (int i = 0; i < userNumber; i++) {
            assertTrue(nickNames.contains(nicknamePrefix + i));
        }

        // 5. check Group user status
        List<ChatRoomSettingDTO> resultChatRoomSettingDTOs = resultChatRoomDTO.getChatRoomSettings();

        int notRegistration = userNumber;
        int join = 1;
        for (ChatRoomSettingDTO dto : resultChatRoomSettingDTOs) {
            switch(dto.getStatus()) {
                case NOT_REGISTRATION:
                    notRegistration--;
                    break;
                case JOIN:
                    join--;
                    break;
            }
        }

        assertEquals(0, notRegistration);
        assertEquals(0, join);
    }
}
