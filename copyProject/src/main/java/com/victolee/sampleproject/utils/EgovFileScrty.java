package com.victolee.sampleproject.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

public class EgovFileScrty {

    // ���ϱ�����
    static final char FILE_SEPARATOR = File.separatorChar;

    static final int BUFFER_SIZE = 1024;

    /**
     * ������ ��ȣȭ�ϴ� ���
     *
     * @param String source ��ȣȭ�� ����
     * @param String target ��ȣȭ�� ����
     * @return boolean result ��ȣȭ���� True/False
     * @exception Exception
     */
    public static boolean encryptFile(String source, String target) throws Exception {

		// ��ȣȭ ����
		boolean result = false;
	
		String sourceFile = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		String targetFile = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcFile = new File(sourceFile);
	
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
	
		byte[] buffer = new byte[BUFFER_SIZE];
	
		try {
		    if (srcFile.exists() && srcFile.isFile()) {
	
			input = new BufferedInputStream(new FileInputStream(srcFile));
			output = new BufferedOutputStream(new FileOutputStream(targetFile));
	
			int length = 0;
			while ((length = input.read(buffer)) >= 0) {
			    byte[] data = new byte[length];
			    System.arraycopy(buffer, 0, data, 0, length);
			    output.write(encodeBinary(data).getBytes());
			    output.write(System.getProperty("line.separator").getBytes());
			}
			result = true;
		    }
		} catch (Exception ex) {
		    //ex.printStackTrace();
		    throw new RuntimeException(ex);	// 2011.10.10 �������� �ļ���ġ
		} finally {
		   if (input != null) {
		       try {
			   input.close();
		       } catch (Exception ignore) {
			   // no-op
		           //ignore.printStackTrace();
				   System.out.println("IGNORE: " + ignore);	// �������� �ļ���ġ
		       }
		   }
		   if (output != null) {
		       try {
			   output.close();
		       } catch (Exception ignore) {
			   // no-op
		           //ignore.printStackTrace();
		           System.out.println("IGNORE: " + ignore);	// �������� �ļ���ġ
		       }
		   }
		}
		return result;
    }

    /**
     * ������ ��ȣȭ�ϴ� ���
     *
     * @param String source ��ȣȭ�� ����
     * @param String target ��ȣȭ�� ����
     * @return boolean result ��ȣȭ���� True/False
     * @exception Exception
     */
    public static boolean decryptFile(String source, String target) throws Exception {

		// ��ȣȭ ����
		boolean result = false;
	
		String sourceFile = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		String targetFile = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcFile = new File(sourceFile);
	
		BufferedReader input = null;
		BufferedOutputStream output = null;
	
		//byte[] buffer = new byte[BUFFER_SIZE];
		String line = null;
	
		try {
		    if (srcFile.exists() && srcFile.isFile()) {
	
			input = new BufferedReader(new InputStreamReader(new FileInputStream(srcFile)));
			output = new BufferedOutputStream(new FileOutputStream(targetFile));
	
			while ((line = input.readLine()) != null) {
			    byte[] data = line.getBytes();
			    output.write(decodeBinary(new String(data)));
			}
	
			result = true;
		    }
		} catch (Exception ex) {
		    //ex.printStackTrace();
		    throw new RuntimeException(ex);	// �������� �ļ���ġ
		} finally {
		   if (input != null) {
		       try {
			   input.close();
		       } catch (Exception ignore) {
			   // no-op
		           //ignore.printStackTrace();
				   System.out.println("IGNORE: " + ignore); // �������� �ļ���ġ
		       }
		   }
		   if (output != null) {
		       try {
			   output.close();
		       } catch (Exception ignore) {
			   // no-op
		    	   //ignore.printStackTrace();
				   System.out.println("IGNORE: " + ignore); // �������� �ļ���ġ
		       }
		   }
		}
		return result;
    }

    /**
     * �����͸� ��ȣȭ�ϴ� ���
     *
     * @param byte[] data ��ȣȭ�� ������
     * @return String result ��ȣȭ�� ������
     * @exception Exception
     */
    public static String encodeBinary(byte[] data) throws Exception {
		if (data == null) {
		    return "";
		}
	
		return new String(Base64.encodeBase64(data));
    }

    /**
     * �����͸� ��ȣȭ�ϴ� ���
     *
     * @param String data ��ȣȭ�� ������
     * @return String result ��ȣȭ�� ������
     * @exception Exception
     */
    @Deprecated
    public static String encode(String data) throws Exception {
    	return encodeBinary(data.getBytes());
    }

    /**
     * �����͸� ��ȣȭ�ϴ� ���
     *
     * @param String data ��ȣȭ�� ������
     * @return String result ��ȣȭ�� ������
     * @exception Exception
     */
    public static byte[] decodeBinary(String data) throws Exception {
    	return Base64.decodeBase64(data.getBytes());
    }

    /**
     * �����͸� ��ȣȭ�ϴ� ���
     *
     * @param String data ��ȣȭ�� ������
     * @return String result ��ȣȭ�� ������
     * @exception Exception
     */
    @Deprecated
    public static String decode(String data) throws Exception {
    	return new String(decodeBinary(data));
    }

    /**
     * ��й�ȣ�� ��ȣȭ�ϴ� ���(��ȣȭ�� �Ǹ� �ȵǹǷ� SHA-256 ���ڵ� ��� ����)
     *
     * @param String data ��ȣȭ�� ��й�ȣ
     * @return String result ��ȣȭ�� ��й�ȣ
     * @exception Exception
     */
    public static String encryptPassword(String data) throws Exception {

		if (data == null) {
		    return "";
		}
	
		byte[] plainText = null; // ��
		byte[] hashValue = null; // �ؽ���
		plainText = data.getBytes();
	
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		hashValue = md.digest(plainText);
	
		/*
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(hashValue);
		*/
		return new String(Base64.encodeBase64(hashValue));
    }
    
    /**
     * ��й�ȣ�� ��ȣȭ�ϴ� ���(��ȣȭ�� �Ǹ� �ȵǹǷ� SHA-256 ���ڵ� ��� ����)
     * @param data ��ȣȭ�� ��й�ȣ
     * @param salt Salt
     * @return ��ȣȭ�� ��й�ȣ
     * @throws Exception
     */
    public static String encryptPassword(String data, byte[] salt) throws Exception {

		if (data == null) {
		    return "";
		}
	
		byte[] hashValue = null; // �ؽ���
	
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		md.reset();
		md.update(salt);
		hashValue = md.digest(data.getBytes());
	
		return new String(Base64.encodeBase64(hashValue));
    }
    
    /**
     * ��й�ȣ�� ��ȣȭ�� �н����� ����(salt�� ���� ��츸 ����).
     * 
     * @param data �� �н�����
     * @param encoded �ؽ�ó���� �н�����(Base64 ���ڵ�)
     * @return
     * @throws Exception
     */
    public static boolean checkPassword(String data, String encoded, byte[] salt) throws Exception {
    	byte[] hashValue = null; // �ؽ���
    	
    	MessageDigest md = MessageDigest.getInstance("SHA-256");
    	
    	md.reset();
    	md.update(salt);
    	hashValue = md.digest(data.getBytes());
    	
    	return MessageDigest.isEqual(hashValue, Base64.decodeBase64(encoded.getBytes()));
    }
}