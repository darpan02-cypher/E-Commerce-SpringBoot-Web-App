package com.example.com.e_com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.com.e_com.dto.AuthRequest;
import com.example.com.e_com.dto.AuthResponse;
import com.example.com.e_com.util.JwtUtil;
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")  //Requestmapping -- gives GET by default, so we need to specify POST here for login endpoint
    public AuthResponse login(@RequestBody AuthRequest req) {
        logger.info("Authentication attempt for username={}", req.getUsername());
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        String token = jwtUtil.generateToken(req.getUsername());
        logger.info("Authentication successful for username={}", req.getUsername());
        return new AuthResponse(token);
    }
}
