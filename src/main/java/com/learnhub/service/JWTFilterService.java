package com.learnhub.service;

import com.learnhub.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTFilterService {
    @Value("${jwt.secret}")
    private String secretkey;

    public String generateToken(User user) {
        System.out.println("===== generateToken() called =====");
        System.out.println("User ID: " + user.getId());
        System.out.println("User Email: " + user.getEmail());
        Map<String, Object> userdetails = new HashMap<>();

        userdetails.put("id", user.getId());
        userdetails.put("name", user.getName());
        userdetails.put("email", user.getEmail());
        userdetails.put("role", user.getRole());

        System.out.println("Claims before token generation: " + userdetails);

      String token= Jwts.builder()
                .claims()
                .add(userdetails)
                .subject(user.getEmail())   // Corrected
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 100000 * 60 * 60))
                .and()
                .signWith(getKey())
                .compact();

        System.out.println("Generated Token: " + token);
        return token;
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public Long extractUserId(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        System.out.println("JWT Claims: " + claims);

        Object id = claims.get("id");

        System.out.println("JWT ID: " + id);

        if (id == null) {
            throw new RuntimeException("ID claim not found in JWT");
        }

        if (id instanceof Integer) {
            return ((Integer) id).longValue();
        }

        if (id instanceof Long) {
            return (Long) id;
        }

        return Long.parseLong(id.toString());
    }
}
