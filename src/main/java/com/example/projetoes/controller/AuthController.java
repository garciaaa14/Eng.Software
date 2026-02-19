package com.example.projetoes.controller;

import com.example.projetoes.security.JwtTokenUtil;
import com.example.projetoes.security.JwtUserDetailsService;
import com.example.projetoes.security.dto.LoginRequest;
import com.example.projetoes.security.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email, req.password)
        );

        UserDetails ud = userDetailsService.loadUserByUsername(req.email);
        String token = jwtTokenUtil.generateToken(ud);

        List<String> roles = ud.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .toList();

        return ResponseEntity.ok(new LoginResponse(token, roles));

    }
}
