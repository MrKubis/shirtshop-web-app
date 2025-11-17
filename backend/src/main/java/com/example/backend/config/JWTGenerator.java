package com.example.backend.config;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Date;

import com.example.backend.api.Models.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
//import java.security.KeyPair;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JWTGenerator {
    //private static final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
    private static final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);


    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public JWTGenerator(RsaKeyProperties rsaKeyProperties){
        this.privateKey = rsaKeyProperties.privateKey();
        this.publicKey = rsaKeyProperties.publicKey();
    }

    public Token generateToken(Authentication authentication) {
        String username = authentication.getName();
        Instant currentDate = Instant.now();
        Instant expireDate = currentDate.plusSeconds(3600);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt( Date.from(currentDate))
                .setExpiration(Date.from(expireDate))
                .signWith(privateKey,SignatureAlgorithm.RS256)
                .compact();
        return new Token(token,currentDate,expireDate);
    }
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
        }
    }

}
