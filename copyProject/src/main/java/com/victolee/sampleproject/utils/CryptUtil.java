package com.victolee.sampleproject.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;

public class CryptUtil {

	// SHA256 ��ȣȭ 
	public static String getSHA256(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException{
        MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-256");
        localMessageDigest.update(str.getBytes("UTF-8"));
        byte[] encrypted = localMessageDigest.digest();
        String base64Binary = DatatypeConverter.printBase64Binary(encrypted);
        return base64Binary;
    }
	
	// HMAC SHA256
	public static String hmac(String key, String value) throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"); 
		Mac mac = Mac.getInstance("HmacSHA256"); mac.init(keySpec); 
		byte[] encrypted = mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
		String base64Binary = DatatypeConverter.printBase64Binary(encrypted);
        return base64Binary;
	}
	
	// AES256 ��ȣȭ
	// ��ȣȭ / ��ȣȭ Key�� ������ Map...
    private static HashMap<Integer, String> hmAes256Key = new HashMap<>();

    static {
        hmAes256Key.put(0, "hjdidsflkndlkjgnwekljgnewklrgjne");
        hmAes256Key.put(1, "njnuifwbuebsdbajbfhgbrwubfebdjgh");
        hmAes256Key.put(2, "irwnndsjkngejgnlkenjdangjkhasiwq");
        hmAes256Key.put(3, "eqicjsvhsnxklsjsudeidhdumsjfeenc");
        hmAes256Key.put(4, "kdjdwndiddnejrlfjsdjkdsngnqivxkk");
        hmAes256Key.put(5, "dkaayshcyeisnvoegsuzmfudkswocksd");
        hmAes256Key.put(6, "palfucjrgdbhjbdxldgkdgnebbeflnaa");
        hmAes256Key.put(7, "ndgjdhgnajdnsyfugberuabgjlarbjbr");
        hmAes256Key.put(8, "cxvoijbosdfjonwfjihwnkldfnhwnhjr");
        hmAes256Key.put(9, "dfnkjbhnwfhwiounhriwnidsfnidngid");
    }


    private static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };


    /**
     * ��ȣȭ Key���� ��ȯ�Ѵ�...
     * @param keyNum    key number��
     * @return
     */
    public static String getEncryptKey(int keyNum){

        if(hmAes256Key.containsKey(keyNum) == false){
            return null;

        } // end if

        return hmAes256Key.get(keyNum);

    }


    /**
     * AES256 Key���� Encrypt(��ȣȭ)�ؼ� ��ȯ�Ѵ�...
     * @param keyIndex          Key Index
     * @param decryptCbc        ��ȣȭ��ų Data
     * @return
     */
    public static String getEncryptCbc(int keyIndex, String decryptCbc) throws Exception {

        String sKey = getEncryptKey(keyIndex);
        if(sKey == null){
            return null;

        } // end if

        return getEncryptCbc(sKey, decryptCbc);

    }


    /**
     * AES256 Key���� Encrypt(��ȣȭ)�ؼ� ��ȯ�Ѵ�... (AES/CBC/PKCS5Padding)
     * @param sKey              ��ȣȭ Key��..
     * @param decryptCbc        ��ȣȭ��ų Data
     * @return
     * @throws Exception
     */
    private static String getEncryptCbc(String sKey, String decryptCbc) throws Exception {

        if(decryptCbc == null || decryptCbc.trim().length() == 0){
            return null;
        }

        String encryptedStr = null;

        //Ű 256bit(32�ڸ�)
        String key256 = sKey.substring(0, 256/8);
        byte[] key256Data = key256.getBytes("UTF-8");

        //����� CBC, �е� PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        
        /*
         * java.security.NoSuchAlgorithmException: Cannot find any provider supporting AES/CBC/PKCS7Padding
         * �� ������ �߻��� �ÿ��� �Ʒ� URL�� ������ ��.
         * $JAVA_HOME/jre/lib/security �� �Ʒ��� unlimited strength crypto file�� ���� ���� �Ͻø� �˴ϴ�.
         * https://dukeom.wordpress.com/2013/01/03/aes256-%EC%95%94%ED%98%B8%ED%99%94%EC%8B%9C-java-security-invalidkeyexception-illegal-key-size-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EC%95%88/
         */
        cipher.init(Cipher.ENCRYPT_MODE , new SecretKeySpec(key256Data,"AES") , new IvParameterSpec(ivBytes));

        //AES��ȣȭ
        byte[] encrypted = cipher.doFinal(decryptCbc.getBytes("UTF-8"));

        //Base64 ���ڵ�
        encryptedStr = new String(Base64.encodeBase64(encrypted), "UTF-8");

        return encryptedStr;

    }



    /**
     * AES256 Key���� Decrypt(��ȣȭ)�ؼ� ��ȯ�Ѵ�...
     * @param keyIndex          Key Index
     * @param encryptData       ��ȣȭ�� Data
     * @return
     */
    public static String getDecryptCbc(int keyIndex, String encryptData) throws Exception {

        String sKey = getEncryptKey(keyIndex);
        if(sKey == null){
            return null;

        } // end if

        return getDecryptCbc(sKey, encryptData);

    }


    /**
     * AES256 Key���� Decrypt(��ȣȭ)�ؼ� ��ȯ�Ѵ�... (AES/CBC/PKCS5Padding)
     * @param sKey              ��ȣȭ Key��..
     * @param encryptData       ��ȣȭ�� Data
     * @return
     */
    public static String getDecryptCbc(String sKey, String encryptData) throws Exception {

        if(encryptData == null || encryptData.trim().length() == 0){
            return null;
        }

        String decryptedStr = null;

        //Ű 256bit(32�ڸ�)
        String key256 = sKey.substring(0, 256/8);
        byte[] key256Data = key256.getBytes("UTF-8");

        //����� CBC, �е� PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE , new SecretKeySpec(key256Data,"AES") , new IvParameterSpec(ivBytes));

        //Base64 ���ڵ�
        byte[] textBytes = Base64.decodeBase64(encryptData.getBytes());

        //AES��ȣȭ
        decryptedStr = new String(cipher.doFinal(textBytes), "UTF-8");

        return decryptedStr;

    }
}
