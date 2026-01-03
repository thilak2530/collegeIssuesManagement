package com.practise.security.service;


import com.practise.security.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.function.Function;
@Service
public class JwtService {

    private final String secretKey;
//this will generate secret key problem will occur if we restrat server we must  again
    public JwtService() {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keygen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


//     this we can keep constant secret key
//    public JwtService() {
//        // ✅ Fixed secret key
//        secretKey = Base64.getEncoder().encodeToString("thilaksiri".getBytes());
//    }


    //generates token if username and password is valid
    public String generateToken(Users user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("refId", user.getRefId());
        claims.put("role", user.getRole());


        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())   // ✅ username
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getKey())
                .compact();
    }

    //hasing in difficulty
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    // 🔑 Used by Security Filter
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 🔑 Used by Controllers / Services
    public String extractRefId(String token) {
        return extractAllClaims(token).get("refId", String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername())
                && extractAllClaims(token).getExpiration().after(new Date());
    }
}