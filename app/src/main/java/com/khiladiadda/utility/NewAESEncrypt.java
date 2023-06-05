package com.khiladiadda.utility;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class NewAESEncrypt {

    public static String encrypt(String value) {
        try {
            IvParameterSpec iv = generateIv(AppConstant.Bajaj_encryptKeyFromBP);
            SecretKeySpec skeySpec = new SecretKeySpec(AppConstant.Bajaj_encryptKeyFromBP.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return new String(Base64.encode(encrypted, Base64.DEFAULT));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            byte[] byteEncrypted = encrypted.getBytes();
            IvParameterSpec iv = generateIv(AppConstant.Bajaj_encryptKeyFromBP);
            SecretKeySpec skeySpec = new SecretKeySpec(AppConstant.Bajaj_encryptKeyFromBP.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decode(byteEncrypted, Base64.DEFAULT));
            return new String(original);
        } catch (Exception ex) {
        }
        return null;
    }

    private static IvParameterSpec generateIv(String keyString) throws UnsupportedEncodingException {
        return new IvParameterSpec(keyString.substring(0, 16).getBytes("UTF-8"));
    }

    public String checkSum(String plaintext) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(plaintext.getBytes("UTF-8"));
        } catch (Exception e) {
            md = null;
        }
        StringBuffer ls_sb = new StringBuffer();
        byte raw[] = md.digest();
        for (int i = 0; i < raw.length; i++)
            ls_sb.append(char2hex(raw[i]));
        return String.valueOf(ls_sb);
    }

    public static String char2hex(byte x) {
        char arr[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'
        };
        char c[] = {
                arr[(x & 0xF0) >> 4],
                arr[x & 0x0F]
        };
        return (new String(c));
    }

}
