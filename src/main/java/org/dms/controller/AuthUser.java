package org.dms.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dms.dto.LoginRequest;
import org.dms.dto.RegisterRequest;
import org.dms.dto.Response;
import org.dms.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
//@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthUser {
    private final UserService userService;

    public AuthUser(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.registerUser(registerRequest));
    }

    @GetMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
}
