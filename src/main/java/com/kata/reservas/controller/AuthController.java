package com.kata.reservas.controller;


import com.kata.reservas.dto.LoginDto;
import com.kata.reservas.dto.SignupRequestDTO;
import com.kata.reservas.dto.UserDto;
import com.kata.reservas.security.JwtUtil;
import com.kata.reservas.service.authenntication.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, AuthService authService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDTO signupRequestDTO) {

        if (authService.presentByUsername(signupRequestDTO.getUserName())) {
            return new ResponseEntity<>("User already exists with this username!", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto currentUser = authService.signup(signupRequestDTO);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @CrossOrigin(
            origins = "http://localhost:4200"
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        String jwt = jwtUtil.create(authentication.getName());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwt)
                .body(Map.of(
                        "accessToken", jwt,
                        "user", "admin"
                ));

    }
}
