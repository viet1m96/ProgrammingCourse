package authorization_lib;

import config.AppConfig;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.user_exceptions.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * The `JwtUtil` class provides methods for creating, checking, and decoding JWTs (JSON Web Tokens).
 * It is used for user authentication and authorization.
 */
public class JwtUtil {
    private static SecretKey SECRET;

    /**
     * Initializes the secret key from the configuration.
     *
     * @throws EnvNotExistsException If the environment variable containing the secret key does not exist.
     */
    public static void init() throws EnvNotExistsException {
        SECRET = Keys.hmacShaKeyFor(AppConfig.getJwtSecret().getBytes());
    }

    /**
     * Generates a new JWT for a user.
     *
     * @param username The username to include in the JWT.
     * @return The generated JWT string.
     */
    public static String generateToken(String username) {

        return Jwts
                .builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(SECRET)
                .compact();


    }

    /**
     * Checks the validity of a JWT.
     *
     * @param token The JWT string to check.
     * @throws TokenException If the JWT is invalid (e.g., expired, invalid signature).
     */
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

    /**
     * Retrieves the username from a JWT.
     *
     * @param token The JWT string to decode.
     * @return The username stored in the JWT.
     */
    public static String getUsername(String token) {
        Jws<Claims> info = Jwts.parser()
                .verifyWith(SECRET)
                .build()
                .parseSignedClaims(token);
        return info.getPayload().getSubject();

    }

}
