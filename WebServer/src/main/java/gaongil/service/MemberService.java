package gaongil.service;

import gaongil.domain.Member;
import gaongil.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	@Autowired
	MemberRepository memberRepository;
	
	public Member findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}
}
