package com.group14.termproject.server.service;

import com.group14.termproject.server.AuthTestUtil;
import com.group14.termproject.server.model.UserDTO;
import com.group14.termproject.server.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AuthServiceTests {

    private final List<UserDTO> registeredUsers = new ArrayList<>();
    private final List<UserDTO> unregisteredUsers = new ArrayList<>();
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthTestUtil authTestUtil;

    @Before
    public void init() {
        int TEST_USER_COUNT = 5;
        for (int i = 0; i < TEST_USER_COUNT; i++) {
            registeredUsers.add(authTestUtil.generateRegisteredUserDTO());
            unregisteredUsers.add(authTestUtil.generateUnregisteredUserDTO());
        }
    }

    @After
    public void clear() {
        registeredUsers.clear();
        unregisteredUsers.clear();
    }

    @Test
    public void testValidRegister() {
        for (UserDTO user : unregisteredUsers) {
            // Assume there is no user with the following username
            authService.register(user);
            Assert.assertNotNull(userRepository.findByUsername(user.getUsername()));
        }
    }

    @Test
    public void testRegisterWithSameUsername() {
        for (UserDTO user : registeredUsers) {
            assertThrows(IllegalArgumentException.class, () -> authService.register(user));
        }
    }

    @Test
    public void testValidLogin() throws AuthenticationException {
        for (UserDTO user : registeredUsers) {
            authService.loginAndGetToken(user);
        }
    }

    @Test
    public void testLoginWithWrongPassword() {
        for (UserDTO user : registeredUsers) {
            user.setPassword(Mockito.anyString());
            assertThrows(AuthenticationException.class, () -> authService.loginAndGetToken(user));
        }
    }

    @Test
    public void testLoginWithNonExistingUser() {
        for (UserDTO user : unregisteredUsers) {
            assertThrows(AuthenticationException.class, () -> authService.loginAndGetToken(user));
        }
    }

}
