package ro.msg.edu.jbugs.usermanagement.business.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {

    private static final Logger logger = LogManager.getLogger(Encryptor.class);


    public static final String BAR12345_BAR12345 = "Bar12345Bar12345";

    public static String encrypt(String toEncrypt) {

        String encryptedString = null;
        try {
            String key = BAR12345_BAR12345; // 128 bit key
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());
            encryptedString = new String(encrypted);
        } catch (Exception e) {
            logger.log(Level.INFO, "Failed to encrypt password");
        }
        return encryptedString;
    }

    public static String decrypt(String toDecrypt) {
        String decryptedString = null;
        try {
            String key = BAR12345_BAR12345; // 128 bit key
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            decryptedString = new String(cipher.doFinal(toDecrypt.getBytes()));
        } catch (Exception e) {
            logger.log(Level.ERROR, "Failed to decrypt password");
        }

        return decryptedString;
    }
}