package com.nhnacademy.exam.hotel.controller;

import com.nhnacademy.exam.hotel.adapter.AuthAdapter;
import com.nhnacademy.exam.hotel.dto.LoginRequest;
import com.nhnacademy.exam.hotel.dto.LoginResponse;
import com.nhnacademy.exam.hotel.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AuthAdapter authAdapter;
    private final JwtUtils jwtUtils;

    // 인증 테스트
    @GetMapping("/")
    public String index(
            Authentication authentication,
            Model model
    ) {
        if(authentication.isAuthenticated()){
            model.addAttribute("username", authentication.getName());
        }
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute LoginRequest loginRequest,
            Model model
    ) {

        // 페인으로 로그인 전송
        ResponseEntity<LoginResponse> response = authAdapter.login(loginRequest);

        if(response.getStatusCode() != HttpStatus.OK) {
            // 로그인 실패
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

        // 로그인 성공 시
        String token = response.getBody().accessToken();

        // 인증 객체 생성
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(jwtUtils.getUserIdFromToken(token), null, null);

        // 인증 객체 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

}
