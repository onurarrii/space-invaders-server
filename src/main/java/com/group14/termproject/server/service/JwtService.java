package com.group14.termproject.server.service;

import com.group14.termproject.server.model.User;
import io.jsonwebtoken.JwtException;

import javax.servlet.http.HttpServletRequest;

public interface JwtService {
    /**
     * @param user owner of the token
     * @return a token string
     */
    String createToken(User user);

    /**
     * @param token a JWT
     * @return id of the token's owner
     * @throws JwtException thrown when token is not valid.
     */
    long getUserId(String token) throws JwtException;

    /**
     * @param request an HTTP request
     * @return true if the HTTP request is considered as valid, false otherwise.
     * @throws JwtException             thrown when token cannot be validated as being malformed.
     * @throws IllegalArgumentException thrown when HTTP request does not have token in its header.
     */
    boolean validateHttpRequestAuthentication(HttpServletRequest request) throws JwtException, IllegalArgumentException;

    /**
     * Given an HTTP header value, it extracts the token string.
     *
     * @param header HTTP Authorization header value.
     * @return extracted token
     * @throws IllegalArgumentException thrown when header value is not in a valid form.
     */
    String extractToken(String header) throws IllegalArgumentException;
}
