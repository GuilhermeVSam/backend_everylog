package com.everylog.Everylog.config;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.everylog.Everylog.utilities.KeyUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${security.jwt.private-key}")
    private String privateKeyPath;

    @Value("${security.jwt.public-key}")
    private String publicKeyPath;

    @Bean
    public RSAPrivateKey privateKey() throws Exception {
        return loadPrivateKey(privateKeyPath);
    }

    @Bean
    public RSAPublicKey publicKey() throws Exception {
        return loadPublicKey();
    }

    private RSAPrivateKey loadPrivateKey(String path) throws Exception {
        InputStream inputStream = KeyUtils.class.getClassLoader().getResourceAsStream("keys/private_key.pem");

        if (inputStream == null) {
            throw new IllegalArgumentException("Private key file not found in classpath!");
        }

        try (PEMParser pemParser = new PEMParser(new InputStreamReader(inputStream))) {
            Object parsedObject = pemParser.readObject();

            if (parsedObject instanceof PrivateKeyInfo) {
                return (RSAPrivateKey) new JcaPEMKeyConverter().getPrivateKey((PrivateKeyInfo) parsedObject);
            } else {
                throw new IllegalArgumentException("Invalid private key format. Expected PEM-encoded PrivateKeyInfo.");
            }
        }
    }

    private RSAPublicKey loadPublicKey() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("keys/public_key.pem");

        if (inputStream == null) {
            throw new IllegalArgumentException("Public key file not found in classpath!");
        }

        try (PEMParser pemParser = new PEMParser(new InputStreamReader(inputStream))) {
            Object parsedObject = pemParser.readObject();

            if (parsedObject instanceof SubjectPublicKeyInfo) {
                return (RSAPublicKey) new JcaPEMKeyConverter().getPublicKey((SubjectPublicKeyInfo) parsedObject);
            } else {
                throw new IllegalArgumentException(
                        "Invalid public key format. Expected PEM-encoded SubjectPublicKeyInfo.");
            }
        }
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/auth/login",
                                "/api/auth/signup",
                                "/api/content/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(conf -> conf.jwt(Customizer.withDefaults()))
                .cors(Customizer.withDefaults())
                .exceptionHandling(handling -> handling
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            System.out.println("Access Denied for: " + request.getRequestURI());
                            response.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied");
                        })
                        .authenticationEntryPoint((request, response, authException) -> {
                            System.out.println("Unauthorized for: " + request.getRequestURI());
                            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
                        }));

        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder(RSAPublicKey publicKey) {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    JwtEncoder jwtEncoder(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        var jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}