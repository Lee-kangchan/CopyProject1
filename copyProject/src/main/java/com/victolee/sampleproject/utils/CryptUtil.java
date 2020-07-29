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

	// SHA256 암호화 
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
	
	// AES256 암호화
	// 암호화 / 복호화 Key를 정의한 Map...
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
     * 암호화 Key값을 반환한다...
     * @param keyNum    key number값
     * @return
     */
    public static String getEncryptKey(int keyNum){

        if(hmAes256Key.containsKey(keyNum) == false){
            return null;

        } // end if

        return hmAes256Key.get(keyNum);

    }


    /**
     * AES256 Key값을 Encrypt(암호화)해서 반환한다...
     * @param keyIndex          Key Index
     * @param decryptCbc        암호화시킬 Data
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
     * AES256 Key값을 Encrypt(암호화)해서 반환한다... (AES/CBC/PKCS5Padding)
     * @param sKey              암호화 Key값..
     * @param decryptCbc        암호화시킬 Data
     * @return
     * @throws Exception
     */
    private static String getEncryptCbc(String sKey, String decryptCbc) throws Exception {

        if(decryptCbc == null || decryptCbc.trim().length() == 0){
            return null;
        }

        String encryptedStr = null;

        //키 256bit(32자리)
        String key256 = sKey.substring(0, 256/8);
        byte[] key256Data = key256.getBytes("UTF-8");

        //운용모드 CBC, 패딩 PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        
        /*
         * java.security.NoSuchAlgorithmException: Cannot find any provider supporting AES/CBC/PKCS7Padding
         * 위 오류가 발생할 시에는 아래 URL을 참고할 것.
         * $JAVA_HOME/jre/lib/security 에 아래의 unlimited strength crypto file을 덮어 쓰기 하시면 됩니다.
         * https://dukeom.wordpress.com/2013/01/03/aes256-%EC%95%94%ED%98%B8%ED%99%94%EC%8B%9C-java-security-invalidkeyexception-illegal-key-size-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EC%95%88/
         */
        cipher.init(Cipher.ENCRYPT_MODE , new SecretKeySpec(key256Data,"AES") , new IvParameterSpec(ivBytes));

        //AES암호화
        byte[] encrypted = cipher.doFinal(decryptCbc.getBytes("UTF-8"));

        //Base64 인코딩
        encryptedStr = new String(Base64.encodeBase64(encrypted), "UTF-8");

        return encryptedStr;

    }



    /**
     * AES256 Key값을 Decrypt(복호화)해서 반환한다...
     * @param keyIndex          Key Index
     * @param encryptData       암호화된 Data
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
     * AES256 Key값을 Decrypt(복호화)해서 반환한다... (AES/CBC/PKCS5Padding)
     * @param sKey              암호화 Key값..
     * @param encryptData       암호화된 Data
     * @return
     */
    public static String getDecryptCbc(String sKey, String encryptData) throws Exception {

        if(encryptData == null || encryptData.trim().length() == 0){
            return null;
        }

        String decryptedStr = null;

        //키 256bit(32자리)
        String key256 = sKey.substring(0, 256/8);
        byte[] key256Data = key256.getBytes("UTF-8");

        //운용모드 CBC, 패딩 PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE , new SecretKeySpec(key256Data,"AES") , new IvParameterSpec(ivBytes));

        //Base64 디코딩
        byte[] textBytes = Base64.decodeBase64(encryptData.getBytes());

        //AES복호화
        decryptedStr = new String(cipher.doFinal(textBytes), "UTF-8");

        return decryptedStr;

    }
}
