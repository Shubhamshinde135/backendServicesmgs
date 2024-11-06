package com.mgstech.util;

import javax.crypto.Cipher;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenEncryDecry {

	  private static byte[] hexStringToByteArray(String s) {
		    int len = s.length();
		    byte[] data = new byte[len / 2];
		    for (int i = 0; i < len; i += 2) {
		      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
		          + Character.digit(s.charAt(i + 1), 16));
		    }
		    return data;
		  }
	
	public String encrypt(String plaintext, String ResEncryptionKey)  {

		try {
		    byte[] keyBytes = hexStringToByteArray(ResEncryptionKey);
		    SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
	
		    String ivString = ResEncryptionKey.substring(0, 16);
		    
		    IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
	
		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		    byte[] encrypted = cipher.doFinal(plaintext.getBytes());
		    return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" Unable to encrypt message  : "+e.getLocalizedMessage());
			return null;
		}
		
	}

	public String decrypt(String ciphertext, String ResEncryptionKey) {

		try {
		    byte[] keyBytes = hexStringToByteArray(ResEncryptionKey);
		    SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
	
		    String ivString = ResEncryptionKey.substring(0, 16);
		    IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
	
		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    cipher.init(Cipher.DECRYPT_MODE, key, iv);
		    byte[] decodedCiphertext = Base64.getDecoder().decode(ciphertext);
		    byte[] decrypted = cipher.doFinal(decodedCiphertext);
		    return new String(decrypted);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" Unable to encrypt message  : "+e.getLocalizedMessage());
			return null;
		}
	}
	
	
}