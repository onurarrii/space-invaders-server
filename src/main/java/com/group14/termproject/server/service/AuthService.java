package com.group14.termproject.server.service;

import com.group14.termproject.server.model.User;
import com.group14.termproject.server.model.UserDTO;
import io.jsonwebtoken.JwtException;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    /**
     * @param user holds user information for registration.
     * @return registered user
     * @throws IllegalArgumentException thrown when there exists another user with same username.
     */
    User register(UserDTO user) throws IllegalArgumentException;

    /**
     * @param user contains Username and raw password of the user
     * @return JSON Web Token (JWT) if login process completed successfully.
     * @throws AuthenticationException Thrown when username, password combination does not match.
     */
    String loginAndGetToken(UserDTO user) throws AuthenticationException;

    /**
     * @param request an HTTP request that should be authenticated.
     * @return true if the request's authentication is validated, false otherwise.
     */
    boolean validateHttpRequestAuthentication(HttpServletRequest request) throws JwtException, IllegalArgumentException;
}
