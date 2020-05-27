package com.self.learning.springblog.security;

import com.self.learning.springblog.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

@Service
public class JwtProvider {
    private Key key;

    @PostConstruct
    public void init() {
        key= Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    //generating token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(key)
                .compact();
    }

//    validating token
    public Boolean validateToken(String jwt){
//        System.out.println(jwt);
//        System.out.println("test");
        Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
        return true;
    }

//    getting username from valid token
    public String getUsernameFromJwt(String jwt) {
        Claims claims=Jwts.parser().setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims.getSubject();
    }
}
