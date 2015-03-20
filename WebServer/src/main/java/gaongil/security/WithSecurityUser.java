package gaongil.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class WithSecurityUser extends User {

	private static final long serialVersionUID = 8748985749507191468L;
	
	public WithSecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
}
