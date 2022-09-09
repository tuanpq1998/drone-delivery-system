package hust.tuanpq.finalproject.dronecontrol.utility;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordUtility {
	
	@Value("${passwordsalt}")
	private String SALT;
	
	public String encrypt(String value) {
	    if(value == null){
	        return value;
	    }
	    // SALT is your secret key
	    Key key = new SecretKeySpec(SALT.getBytes(), "AES");
	    try {

	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes()));
	    } catch (Exception exception) {
	        throw new RuntimeException(exception);
	    }
	}

	   public String decrypt(String value) {

	    if(value == null){
	        return value;
	    }
	    // SALT is your secret key
	    Key key = new SecretKeySpec(SALT.getBytes(), "AES");
	    try {
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, key);
	        return new String(cipher.doFinal(Base64.getDecoder().decode(value)));
	    } catch (Exception exception) {
	        throw new RuntimeException(exception);
	    }
	}
}
