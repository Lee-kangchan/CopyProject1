package com.victolee.sampleproject.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringUtils {
	private static final ObjectMapper mapper = new ObjectMapper();

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}
	
    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
    
    // Empty checks
    //-----------------------------------------------------------------------
    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().</p>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
    
    public static boolean isNotBlank(String str) {
    	if (str == null || str.isEmpty())
    		return false;
    	
    	return true;
    }
    
	/**
	 * jackson ObjectMapper 클래스의 readValue 메소드를 wrapping 한 메소드 정의.
	 */
	public static <T> T readValue(String jsonStr, Class<T> type) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(jsonStr, type);
	}
	
	/**
	 * jackson ObjectMapper 클래스의 readValue 메소드를 wrapping 한 메소드 정의.
	 */
	public static <T> T readValue(String jsonStr, TypeReference valueTypeRef) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(jsonStr, valueTypeRef);
	}
}
