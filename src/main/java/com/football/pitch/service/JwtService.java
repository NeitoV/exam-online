package com.football.pitch.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    public String extractUsername(String token);
    public Claims extractAllClaims(String token);
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    public String generateToken(Map<String, Object> extractClaim, UserDetails userDetails);
    public String generateToken(UserDetails userDetails);
    public boolean isTokenValid(String token, UserDetails userDetails);
    public boolean isTokenExpired(String token);
    public Date extractExpiration(String token);
    public List<Map<String, String>> extractRole(String token);

}
