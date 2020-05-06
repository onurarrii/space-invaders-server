package com.group14.termproject.server.api.auth;

import com.group14.termproject.server.model.User;
import com.group14.termproject.server.model.UserDTO;
import com.group14.termproject.server.service.AuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation(value = "Registers given user with username and password",
            notes = "Must provide unique username and password",
            response = Map.class)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Long>> register(@Valid @RequestBody UserDTO user, Errors errors) {
        if (errors.hasFieldErrors()) {
            // Delegate field validation error to API error handler
            throw new IllegalArgumentException(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
        }
        User registeredUser = authService.register(user);
        return new ResponseEntity<>(Collections.singletonMap("id", registeredUser.getId()), HttpStatus.OK);
    }

    @ApiOperation(value = "Login given user with username and password",
            notes = "Must provide correct username and password",
            response = Map.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDTO user) throws AuthenticationException {
        String token = authService.loginAndGetToken(user);
        return new ResponseEntity<>(Collections.singletonMap("token", token), HttpStatus.OK);
    }
}
