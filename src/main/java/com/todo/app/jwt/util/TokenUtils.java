package com.todo.app.jwt.util;

import com.todo.app.jwt.model.Role;
import com.todo.app.jwt.model.User;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TokenUtils {

    public static String generateUserToken(User user, Long duration, String issuer) {
        String privateKeyLocation = "/privateKey.pem";
        PrivateKey privateKey = readPrivateKey(privateKeyLocation);

        JwtClaimsBuilder claimsBuilder = generateJwtClaimsBuilder(user, issuer, duration);

        return claimsBuilder
                .jws() // Set JsonWebSignature headers and sign the claims by moving to JwtSignatureBuilder
                .keyId(privateKeyLocation) // Set a 'kid' signature key id
                .sign(privateKey); // Sign the claims with PrivateKey. 'RS256' algorithm will be used unless a different one has been set with JwtSignatureBuilder.
    }

    private static PrivateKey readPrivateKey(final String privateKeyLocation) {
        try {
            try (InputStream inputStream = TokenUtils.class.getResourceAsStream(privateKeyLocation)) {
                byte[] buffer = new byte[4096];
                int length = inputStream.read(buffer); // Reads the bytes from the input stream and stores them into the buffer array
                String privateKeyEncoded = new String(buffer, 0, length, StandardCharsets.UTF_8); // String constructed decoding the specified subarray of bytes using the specified charset
                return decodePrivateKey(privateKeyEncoded);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static PrivateKey decodePrivateKey(final String privateKeyEncoded) {
        byte[] encodedBytes = Base64.getDecoder().decode(removeBeginEnd(privateKeyEncoded)); // Decodes a Base64 encoded String into a newly-allocated byte array

        // PKCS8EncodedKeySpec represents the ASN.1 encoding of a private key with the given encoded key assumed to be encoded according to the PKCS #8 standard.
        PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(encodedBytes);
        try {
            // KeyFactory object that converts public/private keys of the specified algorithm
            return KeyFactory.getInstance("RSA").generatePrivate(encodedKeySpec); // Generates a private key object from the provided key specification (encodedKeySpec)
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static String removeBeginEnd(String privateKeyEncoded) {
        privateKeyEncoded = privateKeyEncoded.replaceAll("-----BEGIN (.*)", "");
        privateKeyEncoded = privateKeyEncoded.replaceAll("-----END (.*)", "");
        privateKeyEncoded = privateKeyEncoded.replaceAll("\r\n", "");
        privateKeyEncoded = privateKeyEncoded.replaceAll("\n", "");
        return privateKeyEncoded.trim();
    }

    private static JwtClaimsBuilder generateJwtClaimsBuilder(User user, String issuer, Long duration) {
        // Factory class for creating JwtClaimsBuilder which produces signed, encrypted or signed first and then encrypted JWT tokens.
        JwtClaimsBuilder claimsBuilder = Jwt.claims();

        claimsBuilder.issuer(issuer); // Set an issuer 'iss' claim
        claimsBuilder.subject(user.getId().toString()); // Set a subject 'sub' claim (user identifier)
        claimsBuilder.upn(user.getUserName()); // Set a subject 'upn' claim (user principal name)

        List<Role> roles = user.getRoles();
        Set<String> rolesGroups = new HashSet<>();
        roles.forEach((userRole) -> rolesGroups.add(userRole.getName()));
        claimsBuilder.groups(rolesGroups); // Set a multiple value 'groups' claim

        claimsBuilder.issuedAt(currentTimeInSecs());  // Set an issuer 'iat' claim
        claimsBuilder.expiresAt(currentTimeInSecs() + duration);  // Set an issuer 'exp' claim

        return claimsBuilder;
    }

    private static int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }


}