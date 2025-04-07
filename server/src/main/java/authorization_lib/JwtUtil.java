package authorization_lib;
import config.AppConfig;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.user_exceptions.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    private static SecretKey SECRET;

    public static void init() throws EnvNotExistsException {
        SECRET = Keys.hmacShaKeyFor(AppConfig.getJwtSecret().getBytes());
    }

    public static String generateToken(String username) {

            return Jwts
                    .builder()
                    .subject(username)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .signWith(SECRET)
                    .compact();


    }

    public static void checkToken(String token) throws TokenException {
        try {
            Jws<Claims> info = Jwts.parser()
                    .verifyWith(SECRET)
                    .build()
                    .parseSignedClaims(token);
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new TokenException();
        }
    }

    public static String getUsername(String token) {
        Jws<Claims> info = Jwts.parser()
                .verifyWith(SECRET)
                .build()
                .parseSignedClaims(token);
        return info.getPayload().getSubject();

    }

}
