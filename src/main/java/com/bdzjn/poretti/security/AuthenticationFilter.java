package com.bdzjn.poretti.security;

import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationFilter extends GenericFilterBean {

    private final AuthorizationService authorizationService;

    private static final String AUTHORIZATION = "X-AUTH-TOKEN";

    @Autowired
    public AuthenticationFilter(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final String token = httpServletRequest.getHeader(AUTHORIZATION);
        if (token != null && !token.isEmpty()) {
            authenticate(token);
        }

        chain.doFilter(request, response);
    }

    private void authenticate(String token) {
        authorizationService.findByToken(token).ifPresent(authorization -> {
            final User user = authorization.getUser();
            final List<SimpleGrantedAuthority> authorities = user.getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.name()))
                    .collect(Collectors.toList());
            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, "", authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        });
    }
}
