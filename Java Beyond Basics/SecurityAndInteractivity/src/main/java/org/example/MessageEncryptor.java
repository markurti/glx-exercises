package org.example;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

public class MessageEncryptor {

    public static String encrypt(String message, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt message: " + e.getMessage());
        }
    }
}
