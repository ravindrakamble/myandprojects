package com.ivd.http.models;

import java.lang.reflect.Type;

public class ContactData implements Type{

	private String user_id;
	private String mobile;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



}
