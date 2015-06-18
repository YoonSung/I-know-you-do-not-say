package gaongil.security;

import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yoon on 15. 6. 18..
 */
public abstract class AbstractTokenGenerator {

    protected static final String TOKEN_DELIMITER = ":";

    abstract String makeTokenSignature(String key, long tokenExpiryTime);
    abstract String[] generateTokenArray(String signatureValue, long tokenExpiryTime);

    public String[] getTokenArray(String key, long tokenExpiryTime) {
        String signatureValue = makeTokenSignature(key, tokenExpiryTime);
        return generateTokenArray(signatureValue, tokenExpiryTime);
    }

    protected String generateEncryptString(String data) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }

        return new String(Hex.encode(digest.digest(data.getBytes())));
    }
}
