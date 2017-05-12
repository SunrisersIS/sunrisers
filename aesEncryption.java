/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author chaitanya
 */
public class aesEncryption
{
    private static final String ALGO = "AES";
    
    public static  void main(String args[]) throws Exception
    {
        String key="abc";
        String s=encrypt("message",key);
        String k=decrypt(s,key);
        System.out.println("chatapplication.aesEncryption.main()"+k);
    }
    
    private static Key generateKey(String key1) throws Exception {
        byte[] keyValue = key1.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyValue = sha.digest(keyValue);
        keyValue = Arrays.copyOf(keyValue, 32);        
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }

    public static String encrypt(String Data,String key1) throws Exception {
            Key key = generateKey(key1);
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(Data.getBytes());
            String encryptedValue = DatatypeConverter.printBase64Binary(encVal);
            return encryptedValue;
    }

    public static String decrypt(String encryptedData,String key1) throws Exception {
        Key key = generateKey(key1);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);       
        byte[] decordedValue = DatatypeConverter.parseBase64Binary(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
}