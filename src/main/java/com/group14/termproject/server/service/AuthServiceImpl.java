package com.group14.termproject.server.service;

import com.group14.termproject.server.model.User;
import com.group14.termproject.server.model.UserDTO;
import com.group14.termproject.server.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@Service
class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService,
                           BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public User register(UserDTO user) throws IllegalArgumentException {
        User dbUser = modelMapper.map(user, User.class);
        dbUser.setPassword(bCryptPasswordEncoder.encode(dbUser.getPassword()));
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("There exists another user with same username.");
        }
        return userRepository.save(dbUser);
    }

    @Override
    public String loginAndGetToken(UserDTO userDTO) throws AuthenticationException {
        User user = this.userRepository.findByUsername(userDTO.getUsername());
        if (user != null && bCryptPasswordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            return jwtService.createToken(user);
        }
        throw new AuthenticationException("Username password combination is not valid.");
    }

    @Override
    public boolean validateHttpRequestAuthentication(HttpServletRequest request) {
        return jwtService.validateHttpRequestAuthentication(request);
    }

}
