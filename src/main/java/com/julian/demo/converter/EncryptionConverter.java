package com.julian.demo.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Converter
public class EncryptionConverter implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES";
    private static final String MASTER_KEY = "0x4a, 0x3b, 0x2c, 0x1d, 0x5e, 0x6f, 0x7e, 0x8d, 0x9a, 0xb9, 0xc8, 0xd7, 0xe6, 0xf5, 0x0a, 0x1b,\n" +
            "0x4c, 0x3d, 0x2e, 0x1f, 0x5a, 0x6b, 0x7a, 0x8b, 0x9c, 0xbd, 0xce, 0xdf, 0xea, 0xfb, 0x0c, 0x1d";

    private static SecretKey getMasterKey() {
        SecretKey masterKey = null;
        if (masterKey == null) {
            try {
                byte[] encodedKey = Base64.getDecoder().decode(MASTER_KEY);
                masterKey = new SecretKeySpec(encodedKey, ALGORITHM);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return masterKey;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            SecretKey masterKey = getMasterKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, masterKey);
            byte[] encryptedBytes = cipher.doFinal(attribute.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            SecretKey masterKey = getMasterKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, masterKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(dbData));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}