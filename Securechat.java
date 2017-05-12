    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication;




import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;


public class Securechat  {

       

	public String secureMessage(String message, String public_key) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, Exception{
            //generating public key
             BASE64Decoder decoder = new BASE64Decoder();
            // converting string to byte array 
            byte[] sigBytes2 = decoder.decodeBuffer(public_key);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(sigBytes2);
            KeyFactory keyFact = KeyFactory.getInstance("RSA");
            //converting public key byte array  to public key
           PublicKey publicKey = keyFact.generatePublic(x509KeySpec);
            
            
            
		SecretKey sKey=generateKEY();
                SecretKey iKey=generateKEY();
                String encodediKey = Base64.getEncoder().encodeToString(iKey.getEncoded());
                String encodedsKey = Base64.getEncoder().encodeToString(sKey.getEncoded());
                IvParameterSpec IV=generateIV();
                byte [] init=IV.getIV();
                String key=encodedsKey+encodediKey;
                byte[] rkey=RSAencrypt(key,publicKey);// Encrypting keys with public key of the receiver
                //String s="how are you?";
		byte[] c = encrypt(message,sKey,IV);
                byte[] tag=Htag(c,iKey);
                byte[] result=new byte[c.length+tag.length];
                System.arraycopy(c, 0, result, 0, c.length); 
               System.arraycopy(tag, 0, result, c.length,tag.length);
               byte[] result1=new byte[result.length+rkey.length];
               System.arraycopy(result, 0, result1, 0, result.length); 
               System.arraycopy(rkey, 0, result1,result.length ,rkey.length);
               byte[]result2=new byte[result1.length+init.length];
               System.arraycopy(result1, 0, result2, 0, result1.length); 
               System.arraycopy(init, 0, result2,result1.length ,init.length);
               String st=Arrays.toString(result2);
               return st;
	}
      
       
	public static byte[] RSAencrypt(String plainText,PublicKey pKey) throws Exception {

               Provider bcProvider = new BouncyCastleProvider();
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding",bcProvider);
		cipher.init(Cipher.ENCRYPT_MODE,pKey);
		byte[] c=cipher.doFinal(plainText.getBytes("UTF-8"));
                return c;
	}
        
        public static byte[] encrypt(String plainText,SecretKey skey,IvParameterSpec IV) throws Exception {

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE,skey,IV);
		byte[] c=cipher.doFinal(plainText.getBytes("UTF-8"));
                return c;
	}
       

	public static byte[] Htag(byte[] c,SecretKey ikey)throws NoSuchAlgorithmException, InvalidKeyException{

		Mac sha256HMAC = Mac.getInstance("HmacSHA256");
		sha256HMAC.init(ikey);
		byte[] tag=sha256HMAC.doFinal(c);
           	return tag;
	}
        public static byte[] RSAdecrypt(byte[] res1,PrivateKey pk) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
              Provider bcProvider = new BouncyCastleProvider();
              final Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding",bcProvider);
              cipher.init(Cipher.DECRYPT_MODE, pk);
              byte[] key = cipher.doFinal(res1);
              return key;

        }
	public static String decrypt(String res, String private_key) throws Exception{
             BASE64Decoder decoder = new BASE64Decoder();
                          KeyFactory keyFact = KeyFactory.getInstance("RSA");
                       byte[] sigBytes1= decoder.decodeBuffer(private_key);
                       PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(sigBytes1);
            //converting private key byte array to private key
            PrivateKey privateKey = keyFact.generatePrivate(keySpec);
            
            
            
            
            String[] byteValues = res.substring(1, res.length() - 1).split(",");
            byte[] bytes = new byte[byteValues.length];
            for (int i=0, len=bytes.length; i<len; i++) {
               
                bytes[i] = Byte.parseByte(byteValues[i].trim());     
            }
           int l=bytes.length; 
           byte[] lrkey=Arrays.copyOfRange(bytes,0,1);
            long value = 0;
            for (int i = 0; i < lrkey.length; i++)
            {
               value += ((long) lrkey[i] & 0xffL) << (8 * i);
            }
            byte[] cipherText= Arrays.copyOfRange(bytes,0,16);
            byte[] tag=  Arrays.copyOfRange(bytes,16,48);
            byte[] rkey=Arrays.copyOfRange(bytes,48,304);
            byte[] iv=Arrays.copyOfRange(bytes,l-16,l);
           byte[] keys=RSAdecrypt(rkey,privateKey);
           byte[] enckey = Arrays.copyOfRange(keys,0,(keys.length)/2);
           byte[] intkey = Arrays.copyOfRange(keys,(keys.length)/2,keys.length);
            String encKey = new String(enckey);
            String intKey = new String(intkey);
            byte[] decodedKey = Base64.getDecoder().decode(encKey);
            SecretKey skey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
            byte[] decodedkey = Base64.getDecoder().decode(intKey);
            SecretKey ikey = new SecretKeySpec(decodedkey, 0, decodedkey.length, "AES");
                IvParameterSpec dps = new IvParameterSpec(iv);
                  byte[] rtag=Htag(cipherText,ikey);
             if(Arrays.equals(rtag, tag))
		{
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey,dps);
			return new String(cipher.doFinal(cipherText),"UTF-8");
		}
		else 
			return "Invalid tag";
           
              
	}
	public static SecretKey generateKEY() throws NoSuchAlgorithmException {

		KeyGenerator keyGen = KeyGenerator.getInstance("AES"); 
		SecureRandom sRandom = SecureRandom.getInstanceStrong(); 
		keyGen.init(256, sRandom);
		SecretKey sKey = keyGen.generateKey();
                return sKey;

	}
	public static IvParameterSpec generateIV() {


		SecureRandom r = new SecureRandom();
		r.setSeed(r.generateSeed(16));
		byte[] byteIV = new byte[16];
		r.nextBytes(byteIV);
		IvParameterSpec IV = new IvParameterSpec(byteIV);
		return IV;
	}

    public static void saveToFile(String fileName,BigInteger mod, BigInteger exp) throws IOException {
  ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
  try {
    oout.writeObject(mod);
    oout.writeObject(exp);
  } catch (Exception e) {
    throw new IOException("Unexpected error", e);
  } finally {
    oout.close();
  }
}
}
