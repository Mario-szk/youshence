package com.chanpay.cloud.common.security.utils;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES加密工具类
 * @author Warmsheep
 *
 */
public class EncryptUtil {
	
	private static final String model = "DESede/ECB/PKCS5Padding";//填充模式(PKCS5Padding)   选择不自动应用任何填充(NoPadding)

	/**
	 * DES解密
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String desDecrypt(String message, String key) throws Exception {
		try {
			byte[] keyBytes = null;
			if(key.length() == 16){
				keyBytes = newInstance8Key(ByteUtil.convertHexString(key));
			} else if(key.length() == 32){
				keyBytes = newInstance16Key(ByteUtil.convertHexString(key));
			} else if(key.length() == 48){
				keyBytes = newInstance24Key(ByteUtil.convertHexString(key));
			}
			SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");
			Cipher c1 = Cipher.getInstance(model);
			c1.init(2, deskey);
			byte[] retByte = c1.doFinal(ByteUtil.convertHexString(message));
			
			return new String(retByte);
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}
	
	/**
	 * DES解密
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String desDecryptToHex(String message, String key) throws Exception {
		try {
			byte[] keyBytes = null;
			if(key.length() == 16){
				keyBytes = newInstance8Key(ByteUtil.convertHexString(key));
			} else if(key.length() == 32){
				keyBytes = newInstance16Key(ByteUtil.convertHexString(key));
			} else if(key.length() == 48){
				keyBytes = newInstance24Key(ByteUtil.convertHexString(key));
			}
			SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");
			Cipher c1 = Cipher.getInstance(model);
			c1.init(2, deskey);
			byte[] retByte = c1.doFinal(ByteUtil.convertHexString(message));
			
			return ByteUtil.toHexString(retByte);
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}

	
	/**
	 * DES加密
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String desEncrypt(String message, String key) throws Exception {
		byte[] keyBytes = null;
		if(key.length() == 16){
			keyBytes = newInstance8Key(ByteUtil.convertHexString(key));
		} else if(key.length() == 32){
			keyBytes = newInstance16Key(ByteUtil.convertHexString(key));
		} else if(key.length() == 48){
			keyBytes = newInstance24Key(ByteUtil.convertHexString(key));
		}
		
		SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");

		Cipher cipher = Cipher.getInstance(model);
		cipher.init(1, deskey);
		return ByteUtil.toHexString(cipher.doFinal(message.getBytes("UTF-8")));
	}
	
	public static String desEncryptHexString(String message,String key) throws Exception {
		byte[] keyBytes = null;
		if(key.length() == 16){
			keyBytes = newInstance8Key(ByteUtil.convertHexString(key));
		} else if(key.length() == 32){
			keyBytes = newInstance16Key(ByteUtil.convertHexString(key));
		} else if(key.length() == 48){
			keyBytes = newInstance24Key(ByteUtil.convertHexString(key));
		}
		
		SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");

		Cipher cipher = Cipher.getInstance(model);
		cipher.init(1, deskey);
		return ByteUtil.toHexString(cipher.doFinal(ByteUtil.convertHexString(message)));
	}
	

	/*** 
	* MD5加码 生成32位md5码 
	*/
	public static String md5Encrypt(String message) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		byte[] md5Bytes = md5.digest(message.getBytes());
		String hexValue = ByteUtil.toHexString(md5Bytes);
		return hexValue;

	}

	
	private static byte[] newInstance24Key(byte[] key) {
		if ((key != null) && (key.length == 24)) {
			return key;
		}
		System.err.println("密钥长度有误,期望值[24]");
		return null;
	}

	private static byte[] newInstance16Key(byte[] key) {
		if ((key != null) && (key.length == 16)) {
			byte[] b = new byte[24];
			System.arraycopy(key, 0, b, 0, 16);
			System.arraycopy(key, 0, b, 16, 8);
			key = (byte[]) null;
			return b;
		}
		System.err.println("密钥长度有误,期望值[16]");
		return null;
	}

	private static byte[] newInstance8Key(byte[] key) {
		if ((key != null) && (key.length == 8)) {
			byte[] b = new byte[24];
			System.arraycopy(key, 0, b, 0, 8);
			System.arraycopy(key, 0, b, 8, 8);
			System.arraycopy(key, 0, b, 16, 8);
			key = (byte[]) null;
			return b;
		}
		System.err.println("密钥长度有误,期望值[8]");
		return null;
	}

	public static void main(String[] args) throws Exception {
		// 加密密码测试DEMO
//		System.out.println(md5Encrypt("湖南"));
		String key = "87D111C339265D7D87C8A81A88A096A4F138D4439425CBA6";
//		String password = "c19853a1fbac1d78a2f1341da9e4e20cbf3f974f0936fc3c";
		System.out.println("密钥:"+key);
//		System.out.println("加密前的明文:" + password);
		
		String desText = "c19853a1fbac1d78a2f1341da9e4e20cbf3f974f0936fc3c";
		try {
			System.out.println("使用des加密后的密文:" + desText);
			System.out.println("使用des解密后的明文:" + desDecrypt(desText, key));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
