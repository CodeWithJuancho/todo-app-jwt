package com.todo.app.jwt.util;

import com.todo.app.jwt.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TokenUtilsTest {

    @Test
    public void testGenerateToken() {
        User user = new User();

        user.setUserName("Juan");
        user.setId(1L);
        user.setEmail("juan@gmail.com");
        user.setRoles(new ArrayList<>());


        String token = TokenUtils.generateUserToken(user, 600L, "https://todo-app-jwt.com");

        System.out.println(token);

        assertNotNull(token);
    }

}