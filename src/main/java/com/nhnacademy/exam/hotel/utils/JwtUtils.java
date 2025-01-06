package com.nhnacademy.exam.hotel.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secret;

    // 토큰이 올바른지 검증
    public Boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException | ExpiredJwtException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            return false;
        }
    }

    // 토큰에서 id 추출
    public String getUserIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", String.class);
    }

    // 토큰에서 이름 추출
    public String getUserNameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("iss", String.class);
    }

}
