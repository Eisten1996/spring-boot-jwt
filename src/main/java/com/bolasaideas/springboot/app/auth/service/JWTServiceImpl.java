package com.bolasaideas.springboot.app.auth.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Dipper
 * @project spring-boot-jwt
 * @created 16/10/2020 - 15:27
 */
public class JWTServiceImpl implements JWTService {
    @Override
    public String create(Authentication authentication) {
        return null;
    }

    @Override
    public boolean validate(String token) {
        return false;
    }

    @Override
    public Claims getClaims(String token) {
        return null;
    }

    @Override
    public String getUsername(String token) {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) {
        return null;
    }

    @Override
    public String resolve(String token) {
        return null;
    }
}
