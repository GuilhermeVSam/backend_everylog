package com.everylog.Everylog.utilities;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.IOException;
import java.io.StringReader;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class KeyUtils {

    public static RSAPublicKey parsePublicKey(String publicKeyPem) throws IOException {
        System.out.println(publicKeyPem);
        PEMParser pemParser = new PEMParser(new StringReader(publicKeyPem));
        Object parsedObject = pemParser.readObject();

        if (parsedObject instanceof SubjectPublicKeyInfo) {
            SubjectPublicKeyInfo publicKeyInfo = (SubjectPublicKeyInfo) parsedObject;
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            return (RSAPublicKey) converter.getPublicKey(publicKeyInfo);
        } else {
            throw new IllegalArgumentException("Invalid public key format. Expected PEM-encoded SubjectPublicKeyInfo.");
        }
    }

    public static RSAPrivateKey parsePrivateKey(String privateKeyPem) throws IOException {
        System.out.println(privateKeyPem);
        PEMParser pemParser = new PEMParser(new StringReader(privateKeyPem));
        PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(pemParser.readObject());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        return (RSAPrivateKey) converter.getPrivateKey(privateKeyInfo);
    }
}