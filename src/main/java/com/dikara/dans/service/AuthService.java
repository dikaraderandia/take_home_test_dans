package com.dikara.dans.service;

import com.dikara.dans.dto.request.LoginRequest;
import com.dikara.dans.dto.request.RegisterRequest;
import com.dikara.dans.dto.response.LoginResponse;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
