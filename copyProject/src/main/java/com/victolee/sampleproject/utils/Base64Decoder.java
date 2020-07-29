package com.victolee.sampleproject.utils;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sun.misc.BASE64Decoder;

public class Base64Decoder {
	
	public void Base64Decoder() {
	}

	public static String LoginId(String loginId) throws IOException {
		return new String(new BASE64Decoder().decodeBuffer(loginId));
	}

	public static String LoginPwd(String LoginPwd) throws IOException {
		return new String(new BASE64Decoder().decodeBuffer(LoginPwd));
	}
}
