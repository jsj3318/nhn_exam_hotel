package com.nhnacademy.exam.hotel.config;

import com.nhnacademy.exam.hotel.filter.JwtAuthenticationFilter;
import com.nhnacademy.exam.hotel.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CSRF 토큰 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // jwt 토큰 기반 인증을 하기 때문에 세션 비활성화
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 필터 설정
        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtils),
                        UsernamePasswordAuthenticationFilter.class);



        // 세션 로그인 해보는 중엔 위에꺼 전부 비 활성화하기

        // 로그인은 내꺼 쓰고 로그아웃 설정
        http.logout(logout ->
                logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
        );

        return http.build();
    }

}
