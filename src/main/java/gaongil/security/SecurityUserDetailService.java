package gaongil.security;

import gaongil.domain.Member;
import gaongil.domain.User;
import gaongil.service.MemberService;

import java.util.ArrayList;
import java.util.List;

import gaongil.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecurityUserDetailService implements UserDetailsService {

	@Autowired
	private MemberService memberService;

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Member member = memberService.findByEmail(email);
		
		if (member == null) {
			throw new UsernameNotFoundException(String.format("Member Email %s Not Found!", email));
		}
		
		return new WithSecurityUser(email, member.getPassword(), getGrantedAuthorities(member));
	}

	public UserDetails loadWithUserById(Long id) throws UsernameNotFoundException {
		User user = userService.findById(id);

		if (user == null) {
			throw new UsernameNotFoundException("User Id %s Not Found");
		}

		//TODO check construct value
		return new WithSecurityUser(id.toString(), user.getUuid(), getGrantedAuthorities(user));
	}

	//TODO Convert object type to WithUser
	private List<GrantedAuthority> getGrantedAuthorities(Object object) {

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

		if (object instanceof Member) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		} else if (object instanceof User) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		} else {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));

		}

		return grantedAuthorities;
	}
}
