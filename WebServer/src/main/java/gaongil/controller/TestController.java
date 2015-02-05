package gaongil.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping("/{user}")
	public String test(@PathVariable String user) {
		log.info("testRequest User : {}", user);
		
		if (user == null)
			return "false";
		
		return "true";
	}
}