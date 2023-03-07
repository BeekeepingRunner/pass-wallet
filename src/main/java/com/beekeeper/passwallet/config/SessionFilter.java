package com.beekeeper.passwallet.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SessionFilter extends OncePerRequestFilter {

    private static List<String> PERMITTED_PATHS = List.of(
            "/login",
            "/login/proceed",
            "/signup",
            "/signup/proceed"
    );

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (isPermitted(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            final Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("LOGINID")) {
                        filterChain.doFilter(request, response);
                        break;
                    }
                }
            }

            response.sendRedirect("/login");
        }
    }

    private boolean isPermitted(String requestPath) {
        return PERMITTED_PATHS.contains(requestPath);
    }
}