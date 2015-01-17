package com.ivd.http.models;

import java.lang.reflect.Type;

public class RegResponse implements Type{

	private String user_id;
	private String message;
	public String getUserID() {
		return user_id;
	}
	public void setUserID(String userID) {
		this.user_id = userID;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


}
