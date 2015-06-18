package gaongil.security.token;

/**
 * Created by yoon on 15. 6. 19..
 */
public class UserTokenGenerator extends AbstractTokenGenerator {

    private static final String DUMMY_STRING = "WITH_USER";

    private final long id;
    private final String uuid;

    public UserTokenGenerator(long id, String uuid) {
        this.id = id;
        this.uuid = uuid;
    }

    @Override
    String makeTokenSignature(String key, int tokenLifetime, long tokenExpiryTime) {
        String data = this.id + TOKEN_DELIMITER
                + tokenExpiryTime + TOKEN_DELIMITER
                + this.uuid + TOKEN_DELIMITER
                + this.DUMMY_STRING + TOKEN_DELIMITER
                + key;

        return super.generateEncryptString(data);
    }

    @Override
    String[] generateTokenArray(String signatureValue, long tokenExpiryTime) {
        return new String[] {String.valueOf(this.id), Long.toString(tokenExpiryTime), signatureValue, uuid};
    }
}
