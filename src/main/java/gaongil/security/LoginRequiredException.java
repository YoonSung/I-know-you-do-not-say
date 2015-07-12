package gaongil.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
public class LoginRequiredException extends RuntimeException {
	private static final long serialVersionUID = 6735542028670612804L;
}
