package gaongil.service;

import gaongil.domain.Member;
import gaongil.repository.MemberRepository;
import gaongil.security.LoginRequiredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	@Autowired
	MemberRepository memberRepository;

	public Member findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	public Member getCurrentLoginMember() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null)
			throw new LoginRequiredException();
		
		String email = authentication.getName();
		return findByEmail(email);
	}
}