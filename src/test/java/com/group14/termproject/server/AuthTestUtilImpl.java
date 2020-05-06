package com.group14.termproject.server;

import com.github.javafaker.Faker;
import com.group14.termproject.server.model.User;
import com.group14.termproject.server.model.UserDTO;
import com.group14.termproject.server.repository.UserRepository;
import com.group14.termproject.server.service.AuthService;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.function.Supplier;

@Configuration
class AuthTestUtilImpl implements AuthTestUtil {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDTO generateUnregisteredUserDTO() {
        UserDTO user = new UserDTO();
        user.setUsername(generateRandomUniqueUsername());
        user.setPassword(generateRandomPassword());
        return user;
    }

    @Override
    public UserDTO generateRegisteredUserDTO() {
        UserDTO userDTO = generateUnregisteredUserDTO();
        authService.register(userDTO);
        return userDTO;
    }

    @Override
    public User generateRegisteredUser() {
        UserDTO user = generateUnregisteredUserDTO();
        return authService.register(user);
    }

    private String generateRandomPassword() {
        return new PasswordGenerator().generatePassword(8, Arrays.asList(
                new CharacterRule(EnglishCharacterData.Alphabetical, 2),
                new CharacterRule(EnglishCharacterData.Digit, 2))
        );
    }

    /**
     * @return a random and unique username such that there exists no user registered with the same username.
     */
    private String generateRandomUniqueUsername() {
        Supplier<String> randomUsernameSupplier = () -> new Faker().name().fullName();
        String username = randomUsernameSupplier.get();
        while (userRepository.findByUsername(username) != null) {
            username = randomUsernameSupplier.get();
        }
        return username;
    }

}
