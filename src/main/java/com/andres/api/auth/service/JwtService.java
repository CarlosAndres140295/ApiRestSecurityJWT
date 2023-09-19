package com.andres.api.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret.key}") // El valor del SECRET_KEY en el application.properties
    private String SECRET_KEY;

    public String getToken(UserDetails user) {
        return this.getTokenImpl(new HashMap<>(), user);
    }

    private String getTokenImpl(HashMap<String, Object> extraClaims, UserDetails user) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(this.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = this.getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpiration(token));
    }

    public String getUsernameFromToken(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    private Claims getAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T>  claimsResolver){
        final Claims claims = this.getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpiration(String token){
        return this.getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token){
        return this.getClaim(token, Claims::getExpiration);
    }

}
