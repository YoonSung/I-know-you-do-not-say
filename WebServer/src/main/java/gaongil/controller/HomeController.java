package gaongil.controller;

import gaongil.ccs.GcmCcsSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
    private GcmCcsSender mGcmCcsSender;
	
	@RequestMapping("/")
	public @ResponseBody String home() {
		log.info("/ : ROOT Request");
		return "Test!!";
	}
	
	@RequestMapping(value="/test", method= RequestMethod.POST)
	public @ResponseBody String test(String registerId) {
		log.info("RegId : {}", registerId);
		
		return registerId;
	}
	
	@RequestMapping(value="/test/ccm")
	public @ResponseBody String testCCM() {
		String regId = "APA91bEDa54P9evR09ndjhYfjRG4YdiLE8lPZUiM12kcfNDNi6gdj0qyiBrPN7q3wvwJjUtIJvu0BqE5Zfx6wGh8YkyvfuzxrrthxxiaP5tYydvuNIdyl5ozHtIISbXk3pi4Ob5b1DR5TUmZdlVSIwRoMU-djxFWNQ";
		
		mGcmCcsSender.send(regId, "TestMessage!!");
		
		return "testCCM!";
	}
	
}