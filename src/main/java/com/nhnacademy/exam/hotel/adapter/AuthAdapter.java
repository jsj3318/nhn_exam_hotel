package com.nhnacademy.exam.hotel.adapter;

import com.nhnacademy.exam.hotel.dto.LoginRequest;
import com.nhnacademy.exam.hotel.dto.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "authAdapter", url = "http://133.186.241.167:8200")
public interface AuthAdapter {

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest
    );

}
