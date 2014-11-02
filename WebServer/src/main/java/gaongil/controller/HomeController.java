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
	private static String regId;
	
	@RequestMapping("/")
	public @ResponseBody String home() {
		log.info("/ : ROOT Request");
		return "Test!!";
	}
	
	@RequestMapping(value="/test", method= RequestMethod.POST)
	public @ResponseBody String test(String registerId) {
		log.info("RegId : {}", registerId);
		this.regId = registerId;
		
		return registerId;
	}
	
	@RequestMapping(value="/test/ccm")
	public @ResponseBody String testCCM() {
		
		mGcmCcsSender.send(regId, "TestMessage!!");
		
		return "testCCM!";
	}
	
}