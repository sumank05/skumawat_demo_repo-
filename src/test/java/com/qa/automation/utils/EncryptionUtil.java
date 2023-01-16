package com.qa.automation.utils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {

    private Cipher cipher;
    private String domain;

    public EncryptionUtil(String domain) {
        this.domain = domain;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.out.println("Exception occurred during getting instance of cipher: " + e);
        }
    }

    private SecretKeySpec generateSecretKey(String keyPlainText) {
        return new SecretKeySpec(keyPlainText.getBytes(StandardCharsets.UTF_8), "AES");
    }

    private String getKeyString() {
        String[] weekDays =
                {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
        String day = weekDays[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1];
        String keyString = this.domain + day + "0000000000000000000000";
        return keyString.substring(0, 16).toLowerCase();
    }

    private String getIVString(String keyString) {
        StringBuilder sb = new StringBuilder();
        sb.append(keyString);
        sb.reverse();
        return sb.toString();
    }

    public String encrypt(String passwordPlainText) {
        try {
            byte[] passwordBytes = passwordPlainText.getBytes(StandardCharsets.UTF_8);
            String keyString = getKeyString();
            String ivString = getIVString(keyString);
            SecretKeySpec secretKeySpec = generateSecretKey(keyString);

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,
                    new IvParameterSpec(ivString.getBytes(StandardCharsets.UTF_8)));
            byte[] encryptedBytes = cipher.doFinal(passwordBytes);
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("Exception occurred during fetching encrypted password: " + e);
        }
        return null;
    }
}

