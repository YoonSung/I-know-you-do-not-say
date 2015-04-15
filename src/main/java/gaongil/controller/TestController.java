package gaongil.controller;

import gaongil.domain.Member;
import gaongil.support.web.resolver.argument.LoginMember;
import gaongil.support.web.resolver.argument.Response;
import gaongil.support.web.resolver.argument.ResponseApplicationCode;
import gaongil.support.web.status.ApplicationCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping("")
	public String home() {
		log.info("/ : ROOT Request");
		return "Server Operation Success!";
	}
	
	@RequestMapping("/test")
	public Member test(@LoginMember Member member, @Response ResponseApplicationCode responseCode ) {
		responseCode.set(ApplicationCode.CREATE);
		return member;
	}
	
	@RequestMapping("/error")
	public String error() {
		throw new IllegalArgumentException("Username is required.");
	}
}