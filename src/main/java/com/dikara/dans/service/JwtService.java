package com.dikara.dans.service;

import io.jsonwebtoken.Claims;

public interface JwtService {

    String generateToken(String email, String role);

    Claims validate(String token);
}
