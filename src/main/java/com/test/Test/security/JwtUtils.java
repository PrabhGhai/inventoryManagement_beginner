package com.test.Test.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    public static final String SECRET_KEY =
            "my_super_secret_key_for_jwt_token_123456";

    public static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());


    public String generateToken(UserDetails userDetails) {
       return Jwts.builder()
               .setSubject(userDetails.getUsername())
               .addClaims(Map.of("role",userDetails.getAuthorities().iterator().next().getAuthority()))
               .signWith(SignatureAlgorithm.HS256, key)
               .setIssuedAt(new Date())
               .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
               .compact();
    }

    public Claims extractor(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token)
    {
        return extractor(token).getSubject();
    }

    public Date extractExpiration(String token)
    {
        return extractor(token).getExpiration();
    }

    public Boolean vaildateToken(String token, UserDetails userDetails)
    {
        String email = userDetails.getUsername();

        return (extractUsername(token).equals(email) && extractExpiration(token).after(new Date())) ;
    }





}
