package com.example.dischord.global.jwt;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jose.crypto.MACVerifier;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final byte[] secretKeyBytes;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret,
                   @Value("${spring.jwt.access-token-validity}") long accessTokenValidity,
                   @Value("${spring.jwt.refresh-token-validity}") long refreshTokenValidity) {
        this.secretKeyBytes = Base64.getDecoder().decode(secret);
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String createAccessToken(String email) {
        return createToken(email, accessTokenValidity);
    }

    public String createRefreshToken(String email) {
        return createToken(email, refreshTokenValidity);
    }

    private String createToken(String email, long validity) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validity);

        return io.jsonwebtoken.Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(secretKeyBytes), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String token) throws ParseException {
        JWTClaimsSet claims = parseToken(token);
        return claims.getSubject();
    }

    public boolean isTokenExpired(String token) throws ParseException {
        JWTClaimsSet claims = parseToken(token);
        return claims.getExpirationTime().before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            if (signedJWT.verify(new MACVerifier(secretKeyBytes))) {
                parseToken(token);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private JWTClaimsSet parseToken(String token) throws ParseException {
        JWSObject jwsObject = JWSObject.parse(token);
        return JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
    }
}