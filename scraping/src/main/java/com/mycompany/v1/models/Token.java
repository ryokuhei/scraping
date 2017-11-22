package com.mycompany.v1.models;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Token {
	private static int TOKEN_LENGHT = 16;
	
	private Token() {
	}
	
	public static String createToken() {
		byte token[] = new byte[TOKEN_LENGHT];
		StringBuffer buf = new StringBuffer();
		SecureRandom random = null;
		String tokenString = null;
		
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			random.nextBytes(token);
			
			for(int i = 0; i < token.length; i++) {
				buf.append(String.format("%02x", token[i]));
			}
			tokenString = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return tokenString;
		
	}

}
