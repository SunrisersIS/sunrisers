/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author chaitanya
 */
public class genrateRSAkeys {
    
    
    public static void main(String args[]) throws NoSuchAlgorithmException, Exception
    {
        
        
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            KeyFactory fact = KeyFactory.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();
            PublicKey publicKey = kp.getPublic();
            PrivateKey privateKey = kp.getPrivate();
            
            
           byte[] data = publicKey.getEncoded();
           String d1=new String(data);
           String dl=Arrays.toString(data);
            String message="HI";
           // String encKey = new String(publicKey);
          // byte[] publicK = Base64.getDecoder().decode(data);
            //byte[] publicBytes = Base64.getDecoder().decode(convertedPublic);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(data);
KeyFactory keyFactory = KeyFactory.getInstance("RSA");
PublicKey pubKey = keyFactory.generatePublic(keySpec);
            Provider bcProvider = new BouncyCastleProvider();
            byte encryptedMessage[]=RSAencrypt(message, pubKey);
            byte decryptedMessage[]=RSAdecrypt(encryptedMessage, privateKey); 
            System.out.println("Message"+new String(decryptedMessage));
    }     
        
	public static byte[] RSAencrypt(String plainText,PublicKey pKey) throws Exception {

               Provider bcProvider = new BouncyCastleProvider();
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding",bcProvider);
            
		cipher.init(Cipher.ENCRYPT_MODE,pKey);
		byte[] c=cipher.doFinal(plainText.getBytes("UTF-8"));             
                return c;
	}
            public static byte[] RSAdecrypt(byte[] res1,PrivateKey pk) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
               Provider bcProvider = new BouncyCastleProvider();
              Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding",bcProvider);
              cipher.init(Cipher.DECRYPT_MODE, pk);
              byte[] key = cipher.doFinal(res1);
              return key;

        }
    
    
}
