package com.julian.demo.controller;

import com.julian.demo.service.KeyRotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EncryptionController {

    @Autowired
    private KeyRotationService keyRotationService;

    @PostMapping("/rotateMasterKey")
    public void rotateMasterKey(@RequestBody String newMasterKey) {
        keyRotationService.rotateMasterKey(newMasterKey);
    }

    @PostMapping("/encryptDataKey")
    public String encryptDataKey(@RequestBody String dataKey) {
        return keyRotationService.encryptDataKey(dataKey);
    }

    @PostMapping("/decryptDataKey")
    public String decryptDataKey(@RequestBody String encryptedDataKey) {
        return keyRotationService.decryptDataKey(encryptedDataKey);
    }

}
