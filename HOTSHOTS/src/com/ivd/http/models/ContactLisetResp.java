package com.ivd.http.models;

import java.lang.reflect.Type;

public class ContactLisetResp implements Type{

	private String user_id;
	private String message;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


}
