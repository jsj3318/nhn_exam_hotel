package com.nhnacademy.exam.hotel.filter;


import com.nhnacademy.exam.hotel.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 토큰 헤더를 가져온다
        String authHeader = request.getHeader("Authorization");

        // 헤더가 존재하고, 알맞은 단어로 시작하면
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            // 토큰을 가져와서 유효한지 검증한다
            String token = authHeader.substring(7);
            if(jwtUtils.validateToken(token)) {
                // 유효한 토큰이므로, 해당 토큰에 있는 userId를 이용하여 인증 처리
                Integer userId = jwtUtils.getUserIdFromToken(token);

                // 인증 객체 생성
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, null);

                // 인증 객체 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        }


        filterChain.doFilter(request, response);
    }

}
