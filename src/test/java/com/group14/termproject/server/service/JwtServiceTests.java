package com.group14.termproject.server.service;

import com.group14.termproject.server.AuthTestUtil;
import com.group14.termproject.server.model.User;
import io.jsonwebtoken.JwtException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JwtServiceTests {
    @Autowired
    JwtService jwtService;

    @Autowired
    AuthTestUtil authTestUtil;

    private User user;
    private String token;


    @Before
    public void init() {
        this.user = authTestUtil.generateRegisteredUser();
        this.token = jwtService.createToken(user);
    }


    @Test
    public void testValidGetUserIdFromToken() {
        Assert.assertEquals(jwtService.getUserId(token), user.getId());
    }

    @Test
    public void testGetUserIdFromTokenWithDeflectedToken() {
        String deflected = token.substring(1);
        assertThrows(JwtException.class, () -> jwtService.getUserId(deflected));
    }

    @Test
    public void testValidateToken() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JwtServiceImpl.HEADER_STRING, JwtServiceImpl.TOKEN_PREFIX + token);
        Assert.assertTrue(jwtService.validateHttpRequestAuthentication(request));
    }

    @Test
    public void testValidateTokenWithoutAuthorizationHeader() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertThrows(IllegalArgumentException.class, () -> jwtService.validateHttpRequestAuthentication(request));
    }

    @Test
    public void testInvalidateMalformedToken() {
        String deflectedToken = token.substring(1); // Deflect the token
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JwtServiceImpl.HEADER_STRING, JwtServiceImpl.TOKEN_PREFIX + deflectedToken);
        assertThrows(JwtException.class, () -> jwtService.validateHttpRequestAuthentication(request));
    }

    @Test
    public void testExtractToken() {
        String randomString = Mockito.anyString();
        String validTokenHeader = JwtServiceImpl.TOKEN_PREFIX + randomString;
        Assert.assertEquals(randomString, jwtService.extractToken(validTokenHeader));
        Assert.assertThrows(IllegalArgumentException.class, () -> jwtService.extractToken(randomString));

    }

}
