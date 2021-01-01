package com.todo.app.jwt.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilsTest {

    PasswordUtils passwordUtils = new PasswordUtils();

    @Test
    public void testEncodedPassword() {

        passwordUtils.salt = "xTT@wa12Sa/cda4s()";
        passwordUtils.iteration = 15;
        passwordUtils.keyLength = 256;


        assertEquals("c3U5xzLY+mw+H6kHDCpHSLEiZVccZuZ0p9Ctt2vvLxw=", passwordUtils.encode("admin"));
    }

    @Test
    public void testPasswordValidator() {

        assertTrue(passwordUtils.validate("myPassword(1)"));

        assertFalse(passwordUtils.validate("myPassword1"));
        assertFalse(passwordUtils.validate("myPassword()"));
        assertFalse(passwordUtils.validate("mypassword(1)"));
        assertFalse(passwordUtils.validate("MYPASSWORD(1)"));

    }
}