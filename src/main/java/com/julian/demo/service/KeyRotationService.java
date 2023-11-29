package com.julian.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@Service
public class KeyRotationService {

    private static final String ALGORITHM = "AES";
    private static final String ENCRYPTION_PASSWORD = "deadbeef";
    @Autowired
    private TextEncryptor textEncryptor;

    public void rotateMasterKey(String newMasterKey) {
        textEncryptor = Encryptors.text(newMasterKey, "deadbeef");
    }

    public String encryptDataKey(String dataKey) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            SecretKey secretKey = keyGenerator.generateKey();
            TextEncryptor encryptor = Encryptors.text(secretKey.toString(), ENCRYPTION_PASSWORD);
            return encryptor.encrypt(dataKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptDataKey(String encryptedDataKey) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            SecretKey secretKey = keyGenerator.generateKey();
            TextEncryptor encryptor = Encryptors.text(secretKey.toString(), ENCRYPTION_PASSWORD);
            return encryptor.decrypt(encryptedDataKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
