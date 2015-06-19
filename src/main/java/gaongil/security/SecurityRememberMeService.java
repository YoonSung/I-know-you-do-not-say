package gaongil.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.util.StringUtils;

public class SecurityRememberMeService extends AbstractRememberMeServices {

	private static final Logger log = LoggerFactory.getLogger(SecurityRememberMeService.class);
	
	SecurityUserDetailService securityUserDetailService;

	public SecurityRememberMeService(String key, UserDetailsService userDetailsService) {
		super(key, userDetailsService);
		this.securityUserDetailService = (SecurityUserDetailService) userDetailsService;
	}
	
	@Override
	protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
		
		String username = retrieveUserName(successfulAuthentication);
        String password = retrievePassword(successfulAuthentication);
        
		// If unable to find a username and password, just abort as TokenBasedRememberMeServices is
        // unable to construct a valid token in this case.
        if (!StringUtils.hasLength(username)) {
            logger.debug("Unable to retrieve username");
            return;
        }
        
        if (!StringUtils.hasLength(password)) {
        	UserDetails user = securityUserDetailService.loadUserByUsername(username);
            password = user.getPassword();

            if (!StringUtils.hasLength(password)) {
                logger.debug("Unable to obtain password for user: " + username);
                return;
            }
        }
        

        setTokenToUser(request, response, new MemberTokenGenerator(username, password));
	}

    public void setTokenToUser(HttpServletRequest request, HttpServletResponse response, AbstractTokenGenerator tokenGenerator) {
        int tokenLifetime = calculateLoginLifetime();
        long expiryTime = System.currentTimeMillis();
        // SEC-949
        expiryTime += 1000L* (tokenLifetime < 0 ? TWO_WEEKS_S : tokenLifetime);

        setCookie(tokenGenerator.getTokenArray(getKey(), expiryTime), tokenLifetime, request, response);

        /*
        if (logger.isDebugEnabled()) {
            logger.info("Added remember-me cookie for user '" + username + "', expiry: '"
                    + new Date(expiryTime) + "'");
        }
        */
    }

    /**
     * Calculates the validity period in seconds for a newly generated remember-me login.
     * After this period (from the current time) the remember-me login will be considered expired.
     * This method allows customization based on request parameters supplied with the login or information in
     * the <tt>Authentication</tt> object. The default value is just the token validity period property,
     * <tt>tokenValiditySeconds</tt>.
     * <p>
     * The returned value will be used to work out the expiry time of the token and will also be
     * used to set the <tt>maxAge</tt> property of the cookie.
     *
     * See SEC-485.
     *
     * @param request the request passed to onLoginSuccess
     * @param authentication the successful authentication object.
     * @return the lifetime in seconds.
     */
    protected int calculateLoginLifetime(HttpServletRequest request, Authentication authentication) {
        return getTokenValiditySeconds();
    }


    private int calculateLoginLifetime() {
        return getTokenValiditySeconds();
    }

    /**
     * @author : yoon
     *
     * 리턴하는 데이터는 무의미. 이미 auto true로 설정되어 있어서 무조건 성공한다.
     * 만약, 인증을 철회하려면 사용자(프로그래머)가 직접 로직을 구현해서 Exception을 throw해야 한다.
     * 자세한 내용은 AbstractRememberMeServices의 autoLogin함수를 참조
     */
    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response)
            throws RememberMeAuthenticationException, UsernameNotFoundException {

        int cookieTokenLength = cookieTokens.length;

        //Member Login Request
        switch (cookieTokenLength) {
            case 3:
                WithSecurityUser memberUserDetails =  (WithSecurityUser) securityUserDetailService.loadUserByUsername(cookieTokens[0]);
                MemberTokenGenerator memberTokenGenerator = new MemberTokenGenerator(memberUserDetails.getUsername(), memberUserDetails.getPassword());
                checkWithUserDetails(cookieTokens, memberTokenGenerator);
                return memberUserDetails;

                //TODO catch exception. update token to user
                //return checkMemberDetail(cookieTokens);


            case 4:
                WithSecurityUser userDetails =  (WithSecurityUser) securityUserDetailService.loadWithUserById(Long.parseLong(cookieTokens[0]));
                UserTokenGenerator userTokenGenerator = new UserTokenGenerator(Long.valueOf(userDetails.getUsername()), userDetails.getPassword());
                checkWithUserDetails(cookieTokens, userTokenGenerator);
                return userDetails;

                //TODO catch exception. update token to user
                //return checkUserDetails(cookieTokens);
            default:
                throw new InvalidCookieException("Cookie token did not contain 3 "+ "tokens, but contains '" + Arrays.asList(cookieTokens) +"'");
        }
    }

    private UserDetails checkUserDetails(String[] cookieTokens) {
        log.debug("cookieTokens User id : {}", cookieTokens[0]);

        long tokenExpiryTime;

        try {
            tokenExpiryTime = new Long(cookieTokens[1]).longValue();
        } catch (NumberFormatException nfe) {
            throw new InvalidCookieException("Cookie token[1] did not contain a valid number (contained '" +
                    cookieTokens[1] + "')");
        }

        if (isTokenExpired(tokenExpiryTime)) {
            throw new InvalidCookieException("Cookie token[1] has expired (expired on '"
                    + new Date(tokenExpiryTime) + "'; current time is '" + new Date() + "')");
        }

        WithSecurityUser userDetails =  (WithSecurityUser) securityUserDetailService.loadUserByUsername(cookieTokens[0]);

        String expectedTokenSignature = makeTokenSignature(tokenExpiryTime, userDetails.getUsername(), userDetails.getPassword());
        if (!equals(expectedTokenSignature,cookieTokens[2])) {
            throw new InvalidCookieException("Cookie token[2] contained signature '" + cookieTokens[2]
                    + "' but expected '" + expectedTokenSignature + "'");
        }

        return userDetails;
    }

    /**
     * @author yoon
     * cookie length is 3.
     * 1. email, 2. expiryTime, 3. signatureValue, tokenLifetime, request, response);
     */
    private UserDetails checkMemberDetail(String[] cookieTokens) {
        log.debug("cookieTokens Member Email : {}", cookieTokens[0]);

        long tokenExpiryTime;

        try {
            tokenExpiryTime = new Long(cookieTokens[1]).longValue();
        } catch (NumberFormatException nfe) {
            throw new InvalidCookieException("Cookie token[1] did not contain a valid number (contained '" +
                    cookieTokens[1] + "')");
        }

        if (isTokenExpired(tokenExpiryTime)) {
            throw new InvalidCookieException("Cookie token[1] has expired (expired on '"
                    + new Date(tokenExpiryTime) + "'; current time is '" + new Date() + "')");
        }

        WithSecurityUser userDetails =  (WithSecurityUser) securityUserDetailService.loadUserByUsername(cookieTokens[0]);

        //TODO Weak declaration
        MemberTokenGenerator tokenGenerator = new MemberTokenGenerator(userDetails.getUsername(), userDetails.getPassword());
        String expectedTokenSignature = tokenGenerator.makeTokenSignature(getKey(), tokenExpiryTime);

        if (!equals(expectedTokenSignature,cookieTokens[2])) {
            throw new InvalidCookieException("Cookie token[2] contained signature '" + cookieTokens[2]
                    + "' but expected '" + expectedTokenSignature + "'");
        }

        return userDetails;
    }

    private void checkWithUserDetails(String[] cookieTokens, AbstractTokenGenerator tokenGenerator) {
        long tokenExpiryTime;

        try {
            tokenExpiryTime = new Long(cookieTokens[1]).longValue();
        } catch (NumberFormatException nfe) {
            throw new InvalidCookieException("Cookie token[1] did not contain a valid number (contained '" +
                    cookieTokens[1] + "')");
        }

        if (isTokenExpired(tokenExpiryTime)) {
            throw new InvalidCookieException("Cookie token[1] has expired (expired on '"
                    + new Date(tokenExpiryTime) + "'; current time is '" + new Date() + "')");
        }

        String expectedTokenSignature = tokenGenerator.makeTokenSignature(getKey(), tokenExpiryTime);

        if (!equals(expectedTokenSignature,cookieTokens[2])) {
            throw new InvalidCookieException("Cookie token[2] contained signature '" + cookieTokens[2]
                    + "' but expected '" + expectedTokenSignature + "'");
        }
    }

	private boolean isInstanceOfUserDetails(Authentication authentication) {
        return authentication.getPrincipal() instanceof UserDetails;
    }
	
	protected String retrieveUserName(Authentication authentication) {
        if (isInstanceOfUserDetails(authentication)) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        else {
            return authentication.getPrincipal().toString();
        }
    }

    protected String retrievePassword(Authentication authentication) {
        if (isInstanceOfUserDetails(authentication)) {
            return ((UserDetails) authentication.getPrincipal()).getPassword();
        }
        else {
            if (authentication.getCredentials() == null) {
                return null;
            }
            return authentication.getCredentials().toString();
        }
    }
	
	private String makeTokenSignature(long tokenExpiryTime, String username, String password) {
		String data = username + ":" + tokenExpiryTime + ":" + password + ":" + getKey();
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }

        return new String(Hex.encode(digest.digest(data.getBytes())));
	}

	protected boolean isTokenExpired(long tokenExpiryTime) {
        return tokenExpiryTime < System.currentTimeMillis();
    }
	
	/**
     * Constant time comparison to prevent against timing attacks.
     */
    private static boolean equals(String expected, String actual) {
        byte[] expectedBytes = bytesUtf8(expected);
        byte[] actualBytes = bytesUtf8(actual);
        if (expectedBytes.length != actualBytes.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < expectedBytes.length; i++) {
            result |= expectedBytes[i] ^ actualBytes[i];
        }
        return result == 0;
    }

    private static byte[] bytesUtf8(String s) {
        if (s == null) {
            return null;
        }
        return Utf8.encode(s);
    }
    
}