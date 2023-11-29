package com.julian.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Configuration
public class EncryptionConfig {
    @Value("${master.key}")
    private String masterKey;

    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.text(masterKey, "deadbeef");
    }

}
