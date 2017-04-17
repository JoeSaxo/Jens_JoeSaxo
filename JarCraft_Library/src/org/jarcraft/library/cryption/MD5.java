package org.jarcraft.library.cryption;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Random;

public class MD5 {
	
	public static String cryptRandomised(String data) {
		return crypt(randomise(data));
	}
	
	public static String cryptRandomised(String data, int distance) {
		return crypt(randomise(data, distance));
	}
	
	public static String crypt(String data) {
		try {
			byte[] bytes = data.getBytes("ISO-8859-1");
			MessageDigest md5er = MessageDigest.getInstance("MD5");
			byte[] hash = md5er.digest(bytes);
			return bytes2hex(hash);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static boolean equalsRandomised(String cryptedData, String uncryptedData) {
		return encryptRandomisation(uncryptedData, cryptedData) != null;
	}
	
	public static boolean equalsRandomised(String cryptedData, String uncryptedData, int distance) {
		return encryptRandomisation(uncryptedData, cryptedData, distance) != null;
	}
	
	public static boolean equals(String cryptedData, String uncryptedData) {
		return cryptedData.equals(crypt(uncryptedData));
	}

	private static String bytes2hex(byte[] bytes) {
		StringBuffer r = new StringBuffer(32);
		for (int i = 0; i < bytes.length; i++) {
			String x = Integer.toHexString(bytes[i] & 0xff);
			if (x.length() < 2) r.append("0");
			r.append(x);
		}
		return r.toString();
	}
	
	public static String encrypt(int minLength, String stringMD5, int maxLength, String possibleCharacters) {
		return encrypt(minLength, stringMD5, maxLength, possibleCharacters.toCharArray());
	}
	
	public static String encrypt(String stringMD5, int maxLength, String possibleCharacters) {
		return encrypt(1, stringMD5, maxLength, possibleCharacters.toCharArray());
	}
	
	public static String encrypt(int minLength, String stringMD5, String possibleCharacters) {
		return encrypt(minLength, stringMD5, -1, possibleCharacters.toCharArray());
	}
	
	public static String encrypt(String stringMD5, String possibleCharacters) {
		return encrypt(0, stringMD5, -1, possibleCharacters.toCharArray());
	}
	
	public static String encrypt(String stringMD5, int maxLength, char[] possibleCharacters) {
		return encrypt(1, stringMD5, maxLength, possibleCharacters);
	}
	
	public static String encrypt(int minLength, String stringMD5, char[] possibleCharacters) {
		return encrypt(minLength, stringMD5, -1, possibleCharacters);
	}
	
	public static String encrypt(String stringMD5, char[] possibleCharacters) {
		return encrypt(0, stringMD5, -1, possibleCharacters);
	}

	public static String encrypt(int minLength, String stringMD5, int maxLength, char[] possibleCharacters) {
		
		for (int i = minLength - 1; i < maxLength || maxLength == -1; i++) {
			String stringTry = encrypt(stringMD5, "", i, possibleCharacters);
			if (stringTry != null) {
				return stringTry;
			}
		}
		
		return null;
	}
	
	
	private static String encrypt(String stringMD5, String prefix, int length, char[] options) {
		for (int i = 0; i < options.length; i++) {
			String optionTry = prefix + options[i];
			if (length > 0) {
				String checked = encrypt(stringMD5, optionTry, length - 1, options);
				if (checked != null) {
					return checked;
				}
			} else if (length == 0) {
				if (equals(stringMD5, optionTry)) {
					return optionTry;
				}
			}
		}
		return null;
	}
	

	public static String randomise(String oldData) {
		return randomise(oldData, 111);
	}
	
	public static String randomise(String oldData, int distance) {
		if (distance > 111 || distance < 1) distance = 111;
		Random random = new Random();
		int randomIndex, randomDistance;
		randomIndex = random.nextInt(oldData.length());
		randomDistance = random.nextInt(distance) + 1;
		char[] newData = oldData.toCharArray();
		if (random.nextBoolean()) {
			newData[randomIndex] = (char) ( ((int) newData[randomIndex]) + randomDistance);
		} else {
			newData[randomIndex] = (char) ( ((int) newData[randomIndex]) - randomDistance);
		}
		return String.valueOf(newData);
	}

	public static String encryptRandomisation(String oldDataString, String cryptedData) {
		return encryptRandomisation(oldDataString, cryptedData, 111);
	}
	
	public static String encryptRandomisation(String oldData, String cryptedData, int distance) {
		if (distance > 111 || distance < 1) distance = 111;
		for (int j = 0; j < distance || distance == -1; j++) {
			for (int i = 0; i < oldData.length(); i++) {
				for (int sign : new int[]{1, -1}) {
					char[] encryptionTry = oldData.toCharArray();
					encryptionTry[i] = (char) (((int)encryptionTry[i]) + (sign * (j+1)));
					if (equals(cryptedData, String.valueOf(encryptionTry))) {
						return String.valueOf(String.valueOf(encryptionTry));
					}
				}
			}
		}
		return null;
	}
	
	public static String invertRandomisation(String oldData, String newData) {
		
		for (int i = 0; i < oldData.length(); i++) {
			if (!oldData.substring(i, i + 1).equals(newData.substring(i, i + 1))) {
				char[] invertedData = oldData.toCharArray();
				int difference = (((int)invertedData[i]) - ((int)newData.toCharArray()[i]));
				invertedData[i] = (char) ((int)invertedData[i] + difference);
				return String.valueOf(invertedData);
			}
		}
		
		return null;
	}
}
