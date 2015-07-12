package gaongil.web;

import gaongil.domain.User;
import gaongil.dto.UserDTO;
import gaongil.dto.cloud.CloudMessage;
import gaongil.dto.cloud.Strategy4;
import gaongil.dto.cloud.client.DialogForm;
import gaongil.security.SecurityRememberMeService;
import gaongil.security.UserTokenGenerator;
import gaongil.service.UserService;
import gaongil.support.web.resolver.argument.Response;
import gaongil.support.web.resolver.argument.ResponseApplicationCode;
import gaongil.support.web.status.ApplicationCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityRememberMeService securityRememberMeService;

	/**
	 * @author Yoonsung
	 *
	 * Register Request User Scenario
	 * 1. Request new Registration
	 * -> create, set token cookie, response 201
	 *
	 * 2. Already Registered. But Delete Application, Andorid Data is missing. have not token
	 * -> set token cookie. response 200 (welcome back)
	 *
	 * 3. User change device
	 * -> Delete previous data. create new
	 *
	 * 4. new User. but invited already.
	 * -> update regId, uuid. response 201
	 *
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String create(UserDTO userForm, @Response ResponseApplicationCode code) {
		log.debug("UserForm : {}", userForm.toString());

		// Check User Exist
		User selectedUser = userService.findByPhoneNumber(userForm.getPhoneNumber());

		// Case 1.
		if (selectedUser == null) {
			log.debug("Case 1");
			//TODO Add Retry Template
			User createdUser = userService.create(userForm);

			// Add UserToken to Client Cookie
			securityRememberMeService.setTokenToUser(new UserTokenGenerator(createdUser.getId(), createdUser.getUuid()));
			code.set(ApplicationCode.CREATE_NEWDATA);

		// TODO strctly validate
		} else {
			// Case 4.
			if (selectedUser.isInvitedUser()) {
				log.debug("Case 4");
				User createdUser = userService.createByInvitation(selectedUser, userForm);

				// Add UserToken to Client Cookie
				securityRememberMeService.setTokenToUser(new UserTokenGenerator(createdUser.getId(), createdUser.getUuid()));
				code.set(ApplicationCode.UPDATE_DATA);

			// Case 2.
			} else if (selectedUser.isSameUserForm(userForm)) {
				log.debug("Case 2");
				securityRememberMeService.setTokenToUser(new UserTokenGenerator(selectedUser.getId(), selectedUser.getUuid()));
				code.set(ApplicationCode.OK);

			// Case 3.
			} else {
				log.debug("Case 3");
				// TODO 2. announce and confirmation to user
				// check register request, alert to user and confirmation about another register request.
				//code.set(ApplicationCode.CLIENT_DTO);
				//return new CloudMessage(Strategy4.ALREADY_REGISTERED, new DialogForm(""));

				userService.delete(selectedUser);
				User createdUser = userService.create(userForm);

				//Add UserToken to Client Cookie
				securityRememberMeService.setTokenToUser(new UserTokenGenerator(createdUser.getId(), createdUser.getUuid()));
				code.set(ApplicationCode.CREATE_NEWDATA);
			}

		}

		return "";
	}
}