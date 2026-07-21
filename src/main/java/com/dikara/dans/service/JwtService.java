package com.dikara.dans.service;

public interface JwtService {

    String generateToken(String email, String role);

}
