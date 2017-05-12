/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.security.KeyPair;

/**
 *
 * @author Himani Kondra
 */
public class generateRSAkeys {
    
    
    static PrivateKey pkKey;
        
      public static void main(String args[])throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, NoSuchProviderException, Exception
        {//generate public and private keys and save them to a folder
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            KeyFactory fact = KeyFactory.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();
            PublicKey publicKey = kp.getPublic();
            PrivateKey privateKey = kp.getPrivate();
            //converting public key  and private key to byte array
            byte privkey[]=privateKey .getEncoded();
            byte pubkey[]=publicKey.getEncoded();
            BASE64Encoder encoder = new BASE64Encoder();
            //converting byte array of public key to string
            String prkey= encoder.encode(privkey);
            
            String publiky= encoder.encode(pubkey);
            BASE64Decoder decoder = new BASE64Decoder();
            // converting string to byte array 
            byte[] sigBytes2 = decoder.decodeBuffer(publiky);
            // converting private key string to byte array 
            byte[] sigBytes1= decoder.decodeBuffer(prkey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(sigBytes2);
            KeyFactory keyFact = KeyFactory.getInstance("RSA");
            //converting public key byte array  to public key
            publicKey = keyFact.generatePublic(x509KeySpec);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(sigBytes1);
            //converting private key byte array to private key
            PrivateKey privKey = keyFact.generatePrivate(keySpec);
            //
           //return publicKey;
           Securechat  s=new Securechat();
          
           String k=s.secureMessage("hi", publiky);
            System.out.println(k);
            String k1=s.decrypt(k, prkey);
            System.out.println(k1);
            
           
                   }
      
      
}
