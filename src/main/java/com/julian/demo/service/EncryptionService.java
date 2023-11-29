package com.julian.demo.service;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionService {
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "mySecretKey";

    private TextEncryptor textEncryptor;

    public EncryptionService() {
        textEncryptor = Encryptors.text(SECRET_KEY, "deadbeef");
    }

    public String generateMasterKey() {
        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
//            keyGenerator.init(256);
//            SecretKey masterKey = keyGenerator.generateKey();
//            return Base64.getEncoder().encodeToString(masterKey.getEncoded());

            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(256);
            SecretKey masterKey = keyGenerator.generateKey();
            return masterKey.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String encryptDataKey(String dataKey, String masterKey) {
        try {
            SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(masterKey), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(dataKey.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptDataKey(String encryptedDataKey, String masterKey) {
        try {
            SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(masterKey), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedDataKey));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
