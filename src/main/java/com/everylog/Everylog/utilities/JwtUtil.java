package com.everylog.Everylog.utilities;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    private static final long EXPIRATION_TIME = 864_000_000; // 10 days

    // Load the private key for signing JWTs
    private PrivateKey getPrivateKey() {
        try {
            // Read the private key from the file
            String privateKeyPEM = new String(
                    getClass().getClassLoader().getResourceAsStream("keys/private_key.pem").readAllBytes())
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            // Decode the Base64-encoded private key
            byte[] decodedKey = Base64.getDecoder().decode(privateKeyPEM);

            // Generate the PrivateKey object
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load private key", e);
        }
    }

    // Load the public key for verifying JWTs
    private PublicKey getPublicKey() {
        try {
            // Read the public key from the file
            String publicKeyPEM = new String(
                    getClass().getClassLoader().getResourceAsStream("keys/public_key.pem").readAllBytes())
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            // Decode the Base64-encoded public key
            byte[] decodedKey = Base64.getDecoder().decode(publicKeyPEM);

            // Generate the PublicKey object
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load public key", e);
        }
    }

    // Generate a JWT
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256) // Sign with the private key
                .compact();
    }

    // Validate and parse a JWT
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey()) // Verify with the public key
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}