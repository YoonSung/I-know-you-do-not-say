package controller;

import gaongil.ccs.GcmCcsSender;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/chat")
public class ChatController {
	
	@Autowired
    private GcmCcsSender mGcmCcsSender;
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public @ResponseBody boolean chat(String regId, String message) {
		
		if (regId == null || message == null)
			return false;
		
		//TODO if Request regId Exists
		
		//TODO Then Send Message to Request Group
		
		//Test Code TODO DELET 
		mGcmCcsSender.send(regId, message+new Random().nextInt(4));
		
		return true;
	}
}
