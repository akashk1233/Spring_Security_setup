package com.springsecurity.Security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtService {
    private String scretkey;
    private SecretKey sk;
    public JwtService() {

        try {
            KeyGenerator keygen =KeyGenerator.getInstance("HmacSHA256");
             sk=keygen.generateKey();
            scretkey =Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
    public String getToken(String username){


        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60+60))
                .and()
                .signWith(getkey())
                .compact();
    }

    public  Key getkey() {
        byte[] keyByte=Base64.getDecoder().decode(scretkey);
        return Keys.hmacShaKeyFor(keyByte);
    }


    public String getUserNameFromToken(String token) {
       Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(sk).build().parseSignedClaims(token).getPayload();
    }

    public boolean tokenValidator(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !istokenEpired(token));
    }

    private boolean istokenEpired(String token) {
        Claims claims = extractClaims(token);
        return (claims.getExpiration()).before(new Date());
    }
}
