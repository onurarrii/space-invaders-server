package com.group14.termproject.server.annotations;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordConstraintValidatorTests {

    @Mock
    private ConstraintValidatorContext context;
    private final ConstraintValidator<ValidPassword, String> validator = new PasswordConstraintValidator();

    @Before
    public void setup() {
        context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder =
                mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(Mockito.anyString()))
                .thenReturn(constraintViolationBuilder);
    }

    @Test
    public void testPasswordLength() {

        boolean shortPassword = validator.isValid("a", context);
        boolean longPassword = validator.isValid("abcdefghijklmnoprstuvyz", context);
        boolean validPassword = validator.isValid("123456", context);

        assertFalse(shortPassword, "Very short password considered valid");
        assertFalse(longPassword, "Very long password considered valid");
        assertTrue(validPassword, "Password with valid length considered invalid");
    }


    @Test
    public void testSpecialCharacters() {

        String message = "A password with special characters considered invalid";

        assertTrue(validator.isValid("~!@#$%^&*(", context), message);
        assertTrue(validator.isValid(")_+-=;'\\,", context), message);
    }

}
