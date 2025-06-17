package hr.fer.zr.backend.init.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import hr.fer.zr.backend.DTOs.LoginDTO;
import hr.fer.zr.backend.domain.UsersEntity;
import hr.fer.zr.backend.service.UserAccountService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

import static hr.fer.zr.backend.init.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    private final UserAccountService userAccountService;
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserAccountService userAccountService) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userAccountService = userAccountService;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            final LoginDTO user = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword()
            ));
        } catch (IOException e) {
            return null;
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        System.out.println(authResult);
        //User user = userAccountService.findUserByEmail((org.springframework.security.core.userdetails.User) authResult.getPrincipal().);
        org.springframework.security.core.userdetails.User user = (User) authResult.getPrincipal();
        UsersEntity usersEntity = userAccountService.findUserByEmail(user.getUsername()).get();
        final String token = JWT.create().withSubject(((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername())
                .withClaim("role", userDetailsService.loadUserByUsername(((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername()).getAuthorities().stream().toList().get(0).toString())
                .withClaim("enabled", usersEntity.isEnabled())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(Algorithm.HMAC512(SECRET));
        response.addHeader("Access-Control-Expose-Headers", HEADER_STRING);
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);

    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
