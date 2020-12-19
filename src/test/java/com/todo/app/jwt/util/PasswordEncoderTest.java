package com.todo.app.jwt.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordEncoderTest {

    PasswordEncoder passwordEncoder = new PasswordEncoder();

    @Test
    public void testEncodedPassword() {

        passwordEncoder.salt = "xTT@wa12Sa/cda4s()";
        passwordEncoder.iteration= 15;
        passwordEncoder.keyLength=256;


        assertEquals("c3U5xzLY+mw+H6kHDCpHSLEiZVccZuZ0p9Ctt2vvLxw=", passwordEncoder.encode("admin"));
    }

}