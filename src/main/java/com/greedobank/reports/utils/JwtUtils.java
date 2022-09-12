package com.greedobank.reports.utils;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class JwtUtils {
    private final SecretKey SECRET;

    @Autowired
    private JwtUtils(@Value("${secret.keyword}") String SECRET) {
        this.SECRET = generateSecretKey(SECRET);
    }

    public String getEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static SecretKey generateSecretKey(String keyword) {
        byte[] decodedKey = Base64.getDecoder().decode(keyword);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
}
