package com.r2.appservices.dao;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UserLocation {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private Long userId;
	@Persistent
	private String latitude;
	@Persistent
	private String longitude;
	@Persistent
	private String altitude;
	@Persistent
	private Date updatedAt;
	@Persistent
	private String description;
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
	private String operationType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getAltitude() {
		return altitude;
	}
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
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
		
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	@Override
	public String toString() {
		return "UserLocation [id=" + id + ", userId=" + userId + ", latitude="
				+ latitude + ", longitude=" + longitude + ", altitude="
				+ altitude + ", updatedAt=" + updatedAt + ", description="
				+ description + ", firstame=" + firstame + ", ringStatus="
				+ ringStatus + ", userEmail=" + userEmail + ", projectName="
				+ projectName + ", startDate=" + startDate + ", returnDate="
				+ returnDate + "]";
	}
	
	
}
