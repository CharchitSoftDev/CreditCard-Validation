package com.NewgenApi.dto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CardEncryption {

	private static byte[] hashCard;

	private static Cipher doEncryption(int cipherMode) {
		Security.addProvider(new BouncyCastleProvider());
		final String keys = "484e695bf5b0b62bf34dcfcb42ee921f";
		Cipher cipher = null;
		Key secretKey = new SecretKeySpec(keys.getBytes(), "AES");
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        IvParameterSpec ivspec = new IvParameterSpec(iv);
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(cipherMode, secretKey, ivspec);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cipher;
	}

	public static String encryptCardDetails(String cardNum) {
		try {
			hashCard = doEncryption(Cipher.ENCRYPT_MODE).doFinal(cardNum.getBytes());
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		System.out.println(Base64.getEncoder().encodeToString(hashCard));
		return Base64.getEncoder().encodeToString(hashCard);
	}

	public static String decryptCardDetails(String cardNum) {
		System.out.println(cardNum);
		try {
			hashCard = doEncryption(Cipher.DECRYPT_MODE).doFinal(Base64.getDecoder().decode(cardNum));
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		System.out.println(new String(hashCard));
		return new String(hashCard);
	}
}
