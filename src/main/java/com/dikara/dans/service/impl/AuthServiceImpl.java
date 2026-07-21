package com.dikara.dans.service.impl;

import com.dikara.dans.dto.request.LoginRequest;
import com.dikara.dans.dto.request.RegisterRequest;
import com.dikara.dans.dto.response.LoginResponse;
import com.dikara.dans.entity.Role;
import com.dikara.dans.entity.User;
import com.dikara.dans.exception.DuplicateResourceException;
import com.dikara.dans.repository.UserRepository;
import com.dikara.dans.service.AuthService;
import com.dikara.dans.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;



    @Override
    public void register(RegisterRequest request) {

        if (userRepository.findByEmail(
                request.getEmail()).isPresent()) {

            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(String.valueOf(Role.USER))
                .build();

        userRepository.save(user);

    }

    @Override
    public LoginResponse login(
            LoginRequest request
    ) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        String token =
                jwtService.generateToken(
                        user.getEmail(),
                        user.getRole()
                );

        return LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
