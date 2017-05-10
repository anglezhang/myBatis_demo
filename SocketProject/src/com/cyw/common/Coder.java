package com.cyw.common;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class Coder extends BaseCoder {
	public static final String KEY_ALGORTHM="RSA";//
	public static final String SIGNATURE_ALGORITHM="MD5withRSA";
	
	public static final String PUBLIC_KEY = "RSAPublicKey";//��Կ
	public static final String PRIVATE_KEY = "RSAPrivateKey";//˽Կ
	
	public static void main(String[] args) {
		try {
			Map<String, Object> keyMap = Coder.initKey();
			System.out.println(Coder.getPrivateKey(keyMap));
			System.out.println(Coder.getPublicKey(keyMap));
			String data = Coder.encryptBASE64(Coder.encryptByPrivateKey("����".getBytes(), Coder.getPrivateKey(keyMap)));
			System.out.println(data);
			System.out.println(new String(Coder.decryptByPublicKey(Coder.decryptBASE64(data), Coder.getPublicKey(keyMap))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ʼ����Կ
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> initKey()throws Exception{
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		
		//��Կ
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		//˽Կ
		RSAPrivateKey privateKey =  (RSAPrivateKey) keyPair.getPrivate();
		
		Map<String,Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		
		return keyMap;
	}
	
	/**
	 * ȡ�ù�Կ����ת��ΪString����
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap)throws Exception{
		Key key = (Key) keyMap.get(PUBLIC_KEY);  
		return encryptBASE64(key.getEncoded());     
	}

	/**
	 * ȡ��˽Կ����ת��ΪString����
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception{
		Key key = (Key) keyMap.get(PRIVATE_KEY);  
		return encryptBASE64(key.getEncoded());     
	}
	
	/**
	 * ��˽Կ����
	 * @param data	��������
	 * @param key	��Կ
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data,String key)throws Exception{
		//������Կ
		byte[] keyBytes = decryptBASE64(key);
		//ȡ˽Կ
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		
		//�����ݼ���
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * ��˽Կ���� 
	 * @param data 	��������
	 * @param key	��Կ
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data,String key)throws Exception{
		//��˽Կ����
		byte[] keyBytes = decryptBASE64(key);
		
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		//�����ݽ���
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * �ù�Կ����
	 * @param data	��������
	 * @param key	��Կ
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data,String key)throws Exception{
		//�Թ�Կ����
		byte[] keyBytes = decryptBASE64(key);
		//ȡ��Կ
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		
		//�����ݽ���
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * �ù�Կ����
	 * @param data	��������
	 * @param key	��Կ
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data,String key)throws Exception{
		//��˽Կ����
		byte[] keyBytes = decryptBASE64(key);
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		
		//�����ݽ���
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 *	��˽Կ����Ϣ��������ǩ��
	 * @param data	//��������
	 * @param privateKey	//˽Կ
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data,String privateKey)throws Exception{
		//����˽Կ
		byte[] keyBytes = decryptBASE64(privateKey);
		//����PKCS8EncodedKeySpec����
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		//ָ�������㷨
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		//ȡ˽Կ�׶���
		PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		//��˽Կ����Ϣ��������ǩ��
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateKey2);
		signature.update(data);
		
		return encryptBASE64(signature.sign());
	}
	
	/**
	 * У������ǩ��
	 * @param data	��������
	 * @param publicKey	��Կ
	 * @param sign	����ǩ��
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] data,String publicKey,String sign)throws Exception{
		//���ܹ�Կ
		byte[] keyBytes = decryptBASE64(publicKey);
		//����X509EncodedKeySpec����
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		//ָ�������㷨
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		//ȡ��Կ�׶���
		PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);
		
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicKey2);
		signature.update(data);
		//��֤ǩ���Ƿ�����
		return signature.verify(decryptBASE64(sign));
		
	}
}
