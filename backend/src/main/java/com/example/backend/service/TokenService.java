package com.example.backend.service;

import com.example.backend.api.auth.Token;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final JwtEncoder encoder;

    public TokenService(JwtEncoder jwtEncoder){
        this.encoder = jwtEncoder;
    }

    public Token generateToken(Authentication authentication){
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                //Długość życia tokenu
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope",scope)
                .build();

        return new Token(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(), this.encoder.encode(JwtEncoderParameters.from(claims)).getIssuedAt(), this.encoder.encode(JwtEncoderParameters.from(claims)).getExpiresAt());
    }
}