package com.SistemPos.sistemapos.config.filters;

import com.SistemPos.sistemapos.services.UserServices;
import com.SistemPos.sistemapos.ultis.JwtUltis;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class JwtValidator extends OncePerRequestFilter {

    private final JwtUltis jwtUltis;


    @Autowired
    private UserServices userServices;

    public JwtValidator(JwtUltis jwtUltis) {
        this.jwtUltis = jwtUltis;
    }


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtToken != null) {
            jwtToken = jwtToken.substring(7);
            DecodedJWT decodedJWT = jwtUltis.validateToken(jwtToken);

            String username = jwtUltis.extractUsername(decodedJWT);
            String stringAuthorities = jwtUltis.getSpecificClaim(decodedJWT, "authorities").asString();

            Collection<? extends GrantedAuthority> authorities =
                    AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities)
                            .stream()
                            .map(authority -> {
                                String authorityString = authority.getAuthority();
                                return authorityString.startsWith("ROLE_") ? authorityString : "ROLE_" + authorityString;
                            })
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);
        }
        filterChain.doFilter(request, response);
    }
}