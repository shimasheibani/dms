package org.dms.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
public class JwtUtils {
    private static final Long expirationTime = 100L * 60L * 60L * 24L * 30L * 6L;
    private SecretKey secretKey;
    @Value("${secretJwtString}")
    private String secretJwtString ;

    @PostConstruct
    public void init(){
        if(secretJwtString==null || secretJwtString.isEmpty()){
            throw new RuntimeException("JWT secret key is not configured");
        }
        log.info("JWT secret key is ");
        byte[] keyBytes = secretJwtString.getBytes(StandardCharsets.UTF_8);
        this.secretKey = new SecretKeySpec(keyBytes, "HmaCSHA256");
    }

    public String generateToken (String email){
        return Jwts.builder()
                .subject(email)
                .expiration(new Date(System.currentTimeMillis()+ expirationTime) )
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(secretKey)
                .compact();
    }
    public String getUsernamFromToken(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims (String token, Function<Claims,T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload());
    }
    public  boolean isTokenValid (String token, UserDetails userDetails){
        final String username = getUsernamFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
