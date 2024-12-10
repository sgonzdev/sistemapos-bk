package com.SistemPos.sistemapos.controllers;

import com.SistemPos.sistemapos.dto.request.AuthLoginRequest;
import com.SistemPos.sistemapos.dto.request.AuthRequest;
import com.SistemPos.sistemapos.dto.request.UserRequest;
import com.SistemPos.sistemapos.models.user.UserEntity;
import com.SistemPos.sistemapos.services.AuthServices;
import com.SistemPos.sistemapos.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServices userServices;
    private final AuthServices authServices;

    public AuthController(UserServices userServices, AuthServices authServices) {
        this.userServices = userServices;
        this.authServices = authServices;
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserRequest userRequest) {
        UserEntity savedUser = userServices.saveUser(userRequest);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthRequest> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        return new ResponseEntity<>(this.authServices.login(userRequest), HttpStatus.OK);
    }




}
