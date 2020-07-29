package com.victolee.sampleproject.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String user_email = "";
	private String user_default_email = "";
	private String user_name = "";
	private int user_no = 0;
	private int portal_member_no = 0;
	private String portal_id = "";
	private String use_second_certification = "";
	private String user_contact = "";
	private String birth_date = "";
	private int last_access_company_no = 0;
	private String randomkey = "";
	
	private String hash_key = "";
	private String session_key = "";
	private String auth_a_token = "";
	
	private String sessionTime = "86500"; //default 1day
    

    //UC 유저 정보
    private UserInfo UserInfo;

  
	public String getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(String sessionTime) {
		this.sessionTime = sessionTime;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_default_email() {
		return user_default_email;
	}

	public void setUser_default_email(String user_default_email) {
		this.user_default_email = user_default_email;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}

	public int getPortal_member_no() {
		return portal_member_no;
	}

	public void setPortal_member_no(int portal_member_no) {
		this.portal_member_no = portal_member_no;
	}

	public String getPortal_id() {
		return portal_id;
	}

	public void setPortal_id(String portal_id) {
		this.portal_id = portal_id;
	}

	public String getUse_second_certification() {
		return use_second_certification;
	}

	public void setUse_second_certification(String use_second_certification) {
		this.use_second_certification = use_second_certification;
	}

	public String getUser_contact() {
		return user_contact;
	}

	public void setUser_contact(String user_contact) {
		this.user_contact = user_contact;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public int getLast_access_company_no() {
		return last_access_company_no;
	}

	public void setLast_access_company_no(int last_access_company_no) {
		this.last_access_company_no = last_access_company_no;
	}

	public String getRandomkey() {
		return randomkey;
	}

	public void setRandomkey(String randomkey) {
		this.randomkey = randomkey;
	}

	public String getHash_key() {
		return hash_key;
	}

	public void setHash_key(String hash_key) {
		this.hash_key = hash_key;
	}

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	public String getAuth_a_token() {
		return auth_a_token;
	}

	public void setAuth_a_token(String auth_a_token) {
		this.auth_a_token = auth_a_token;
	}

	public UserInfo getUcUserInfo() {
		return UserInfo;
	}

	public void setUcUserInfo(UserInfo UserInfo) {
		this.UserInfo = UserInfo;
	}

}

