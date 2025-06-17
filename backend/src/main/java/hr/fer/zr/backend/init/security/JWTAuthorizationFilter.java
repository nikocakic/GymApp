package hr.fer.zr.backend.init.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static hr.fer.zr.backend.init.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HEADER_STRING);

        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            final UsernamePasswordAuthenticationToken authenticationToken = getPasswordAuthenticationToken(request);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private static UsernamePasswordAuthenticationToken getPasswordAuthenticationToken(HttpServletRequest request) {
        final String token = request.getHeader(HEADER_STRING);
        UsernamePasswordAuthenticationToken passwordAuthenticationToken = null;

        try {
            if (token != null) {
                DecodedJWT userToken = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build().verify(token.replace(TOKEN_PREFIX, ""));
                final String user = userToken.getSubject();
                if (user != null) {
                    passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, List.of(new SimpleGrantedAuthority(userToken.getClaim("role").toString().replace("\"", ""))));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return passwordAuthenticationToken;
    }
}
