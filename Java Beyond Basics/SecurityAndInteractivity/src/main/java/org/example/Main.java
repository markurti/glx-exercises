package org.example;

import javax.crypto.SecretKey;

public class Main {
    public static void main(String[] args) {
        System.out.println("AES Encryption/Decryption App");

        // Original message
        String originalMessage = "Hi! This is a secret message! 你好,;234😀";
        System.out.println("Original Message: " + originalMessage);

        // Generate secret key
        SecretKey secretKey = SecretKeyGenerator.generateSecretKey();
        String keyString = SecretKeyGenerator.keyToString(secretKey);
        System.out.println("Generated Secret Key: " + keyString.substring(0, 16) + "...");

        // Encrypt the message
        String encryptedMessage = MessageEncryptor.encrypt(originalMessage, secretKey);
        System.out.println("Encrypted Message: " + encryptedMessage);

        // Decrypt the message
        String decryptedMessage = MessageDecryptor.decrypt(encryptedMessage, secretKey);
        System.out.println("Decrypted Message: " + decryptedMessage);

        // Verify match
        boolean success = originalMessage.equals(decryptedMessage);
        System.out.println("Encryption/Decryption " + (success ? "SUCCESS" : "FAILED"));

        // Convert key to string and back
        SecretKey recreatedKey = SecretKeyGenerator.stringToKey(keyString);
        String decryptedWithRecreatedKey = MessageDecryptor.decrypt(encryptedMessage, recreatedKey);

        System.out.println("Decrypted with recreated key: " + decryptedWithRecreatedKey);
        boolean keyPersistenceSuccess = originalMessage.equals(decryptedWithRecreatedKey);
        System.out.println("Encryption/Decryption " + (keyPersistenceSuccess ? "SUCCESS" : "FAILED"));
    }
}