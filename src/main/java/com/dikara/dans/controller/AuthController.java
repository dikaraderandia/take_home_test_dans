package com.dikara.dans.controller;

import com.dikara.dans.common.ApiResponse;
import com.dikara.dans.common.ResponseUtil;
import com.dikara.dans.dto.request.LoginRequest;
import com.dikara.dans.dto.request.RegisterRequest;
import com.dikara.dans.dto.response.LoginResponse;
import com.dikara.dans.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>>
    register(
            @Valid
            @RequestBody
            RegisterRequest request
    ) {

        authService.register(request);

        return ResponseEntity.ok(
                ResponseUtil.success(
                        "Register success"
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>>
    login(
            @Valid
            @RequestBody
            LoginRequest request
    ) {

        return ResponseEntity.ok(
                ResponseUtil.success(
                        "Login success",
                        authService.login(request)
                )
        );
    }
}
