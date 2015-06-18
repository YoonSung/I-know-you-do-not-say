package gaongil.security.token;

/**
 * Created by yoon on 15. 6. 18..
 */
public class MemberTokenGenerator extends AbstractTokenGenerator {

    private final String email;
    private final String password;

    public MemberTokenGenerator(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    String makeTokenSignature(String key, int tokenLifetime, long tokenExpiryTime) {
        String data = this.email + TOKEN_DELIMITER
                + tokenExpiryTime + TOKEN_DELIMITER
                + this.password + TOKEN_DELIMITER
                + key;

        return super.generateEncryptString(data);
    }

    @Override
    String[] generateTokenArray(String signatureValue, long tokenExpiryTime) {
        return new String[] {this.email, Long.toString(tokenExpiryTime), signatureValue};
    }
}
