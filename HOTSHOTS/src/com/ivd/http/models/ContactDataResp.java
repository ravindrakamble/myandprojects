package com.ivd.http.models;

import java.lang.reflect.Type;

public class ContactDataResp implements Type{

	private int matched_contact;

	public int getMatched_contact() {
		return matched_contact;
	}

	public void setMatched_contact(int matched_contact) {
		this.matched_contact = matched_contact;
	}


}
