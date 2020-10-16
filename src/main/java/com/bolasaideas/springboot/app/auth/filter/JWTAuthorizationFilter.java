package com.bolasaideas.springboot.app.auth.filter;

import com.bolasaideas.springboot.app.auth.SimpleGrantedAuthorityMixin;
import com.bolasaideas.springboot.app.models.entities.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Dipper
 * @project spring-boot-jwt
 * @created 16/10/2020 - 10:15
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader("Authorization");

        if (!requiresAuthorization(header)) {
            chain.doFilter(request, response);
            return;
        }
//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        boolean validoToken;
        Claims token = null;
        try {
            token = Jwts.parserBuilder().setSigningKey(JWTAuthenticationFilter.secretKey)
                    .build()
                    .parseClaimsJws(header.replace("Bearer ", "")).getBody();
            validoToken = true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            validoToken = false;
        }

        UsernamePasswordAuthenticationToken authenticationToken = null;
        if (validoToken) {
            String username = token.getSubject();
            Object roles = token.get("authorities");
            Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                    .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                    .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
            authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    protected boolean requiresAuthorization(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return false;
        }
        return true;
    }
}
