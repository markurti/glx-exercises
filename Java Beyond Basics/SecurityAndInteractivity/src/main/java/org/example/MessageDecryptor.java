package org.example;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

public class MessageDecryptor {

    public static String decrypt(String encryptedMessage, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt message: " + e.getMessage());
        }
    }
}
