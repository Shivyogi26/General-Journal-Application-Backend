package net.engineeringdigest.journalApp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    // =========================
    // SECRET KEY (256-BIT)
    // =========================
    private static final String SECRET =
            "veryLongSecretKeyForJwtSigning12345678901234567890";

    private final SecretKey secretKey =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    // =========================
    // TOKEN EXPIRATION (60 MIN)
    // =========================
    private static final long EXPIRATION_TIME =
            1000 * 60 * 60;

    // =========================
    // GENERATE TOKEN
    // =========================
    public String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // =========================
    // EXTRACT USERNAME
    // =========================
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // =========================
    // EXTRACT EXPIRATION
    // =========================
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // =========================
    // CHECK IF TOKEN EXPIRED
    // =========================
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // =========================
    // VALIDATE TOKEN
    // =========================
    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token))
                && !isTokenExpired(token);
    }

    // =========================
    // EXTRACT ALL CLAIMS
    // =========================
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}