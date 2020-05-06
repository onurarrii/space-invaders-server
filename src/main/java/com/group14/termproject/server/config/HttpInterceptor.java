package com.group14.termproject.server.config;

import com.group14.termproject.server.service.AuthService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
class HttpInterceptor implements HandlerInterceptor {

    private final AuthService authService;
    @Value("${custom.security.ignored-endpoints}")
    private String excludedEndpointsRegex;

    public HttpInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean isEndpointExcluded = request.getServletPath().matches(excludedEndpointsRegex);
        if (isEndpointExcluded) return true;

        try {
            return authService.validateHttpRequestAuthentication(request);
        } catch (JwtException | IllegalArgumentException e) {
            response.setStatus(403);
            return false;
        }
    }
}
