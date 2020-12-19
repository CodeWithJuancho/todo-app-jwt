package com.todo.app.jwt.util;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.ApplicationScoped;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@ApplicationScoped
public class PasswordEncoder {

    @ConfigProperty(name = "com.todo.app.jwt.password.salt")
    String salt;

    @ConfigProperty(name = "com.todo.app.jwt.password.iteration")
    Integer iteration;

    @ConfigProperty(name = "com.todo.app.jwt.password.keyLength")
    Integer keyLength;

    public String encode(String password) {

        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(password.toCharArray(), salt.getBytes(), iteration, keyLength))
                    .getEncoded();

            return Base64.getEncoder().encodeToString(result);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
