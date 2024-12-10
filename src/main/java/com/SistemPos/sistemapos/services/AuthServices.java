package com.SistemPos.sistemapos.services;

import com.SistemPos.sistemapos.dto.request.AuthLoginRequest;
import com.SistemPos.sistemapos.dto.request.AuthRequest;
import com.SistemPos.sistemapos.ultis.JwtUltis;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {

    private final UserDetailServices userDetailServices;
    private final JwtUltis jwtUltis;
    private final PasswordEncoder passwordEncoder;

    public AuthServices(UserDetailServices userDetailServices, JwtUltis jwtUltis, PasswordEncoder passwordEncoder) {
        this.userDetailServices = userDetailServices;
        this.jwtUltis = jwtUltis;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthRequest login(AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();
        Authentication authentication = this.authenticated(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUltis.createToken(authentication);
        return new AuthRequest(username,"User longed Successfully", accessToken, true);
    }

    public Authentication authenticated(String username, String password) {
        UserDetails userDetails = this.userDetailServices.loadUserByUsername(username);
        if (userDetails == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }
    return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    }

}
