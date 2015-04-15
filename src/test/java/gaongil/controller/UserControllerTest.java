package gaongil.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import util.TestUtil;

public class UserControllerTest {

	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = TestUtil.getMockMvc(new UserController());
	}
	
	@Test
	public void 사용자_등록요청() throws Exception {
		
		String regId = "test";
		String phoneNumber = "01099258547";
		
		MvcResult result = mockMvc.perform(post("/user")
				.param("regId", regId)
				.param("phoneNumber", phoneNumber)
		)
		.andExpect(status().isOk()).andReturn();
		
		System.out.println(result.getModelAndView().getModel().toString());
	}

}
