package com.r2.appservices.dao;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

public class UserDetails {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String firstame;

	@Persistent
	private int ringStatus;

	@Persistent
	private String userEmail;

	@Persistent
	private String projectName;

	@Persistent
	private String startDate;

	@Persistent
	private String returnDate;
	
	@Persistent
	private Long deviceID;


	public String getFirstame() {
		return firstame;
	}


	public void setFirstame(String firstame) {
		this.firstame = firstame;
	}


	public int getRingStatus() {
		return ringStatus;
	}


	public void setRingStatus(int ringStatus) {
		this.ringStatus = ringStatus;
	}


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getReturnDate() {
		return returnDate;
	}


	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}


	public Long getDeviceID() {
		return deviceID;
	}


	public void setDeviceID(Long deviceID) {
		this.deviceID = deviceID;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


}
