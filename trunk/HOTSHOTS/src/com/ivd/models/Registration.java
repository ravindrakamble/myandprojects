package com.ivd.models;

public class Registration {

	private String device_type;
	private String device_id;
	private String name;
	private String email;
	private String mobile;
	private String ref_id;
	public String getDeviceType() {
		return device_type;
	}
	public void setDeviceType(String deviceType) {
		this.device_type = deviceType;
	}
	public String getDeviceID() {
		return device_id;
	}
	public void setDeviceID(String deviceID) {
		this.device_id = deviceID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRefID() {
		return ref_id;
	}
	public void setRefID(String refID) {
		this.ref_id = refID;
	}


}
