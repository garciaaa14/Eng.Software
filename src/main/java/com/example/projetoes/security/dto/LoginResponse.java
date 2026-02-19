package com.example.projetoes.security.dto;

import java.util.List;

public class LoginResponse {
    public String token;
    public List<String> roles;
    public LoginResponse(String token, List<String> roles) {
        this.token = token;
        this.roles = roles;
    }
}

