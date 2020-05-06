package com.group14.termproject.server.annotations;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class ValidPasswordTests {
    @ValidPassword
    private final String fakeField = "";


    private static ValidPassword extractAnnotationOfField(Field field) {
        return Arrays.stream(field.getAnnotations())
                .filter(ValidPassword.class::isInstance)
                .map(ValidPassword.class::cast)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Test
    public void testDefaultValues() throws NoSuchFieldException {
        ValidPassword validPassword =
                extractAnnotationOfField(ValidPasswordTests.class.getDeclaredField("fakeField"));
        assertNotNull("Default message cannot be initialized", validPassword.message());
        assertArrayEquals("Group field is not empty", new Class[]{}, validPassword.groups());
        assertArrayEquals("Payload field is not empty", new Class[]{}, validPassword.payload());
    }


}