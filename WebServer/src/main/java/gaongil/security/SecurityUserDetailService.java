package gaongil.security;

import gaongil.domain.Member;
import gaongil.service.MemberService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SecurityUserDetailService implements UserDetailsService {

	@Autowired
	private MemberService memberService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Member member = memberService.findByEmail(email);
		
		if (member == null) {
			throw new UsernameNotFoundException(String.format("Member Email %s Not Found!", email));
		}
		
		return new WithSecurityUser(email, member.getPassword(), getGrantedAuthorities(member));
	}

	private List<GrantedAuthority> getGrantedAuthorities(Member member) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
		
		return grantedAuthorities;
	}
	
}
