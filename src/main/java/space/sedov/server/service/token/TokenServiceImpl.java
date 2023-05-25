package space.sedov.server.service.token;

import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class TokenServiceImpl implements TokenServiceInterface {
    public String generateToken() {
        String token = new String();
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            token = new Integer(sr.nextInt(Integer.MAX_VALUE)).toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return token;
    }
}
