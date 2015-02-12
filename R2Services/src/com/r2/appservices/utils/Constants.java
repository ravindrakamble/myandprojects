package com.r2.appservices.utils;

public interface Constants {

	//Response Codes
	int CONTACT_ADDED = 11;
	int CONTACT_UPDATED = 12;
	int CONTACT_DELETED = 13;
	int CONTACT_LIST = 14;
	int ADDRESS_ADDED = 15;
	
	//Response types
	String SUCCESS = "SUCCESS";
	String FAILURE = "FAILURE";
	
	//Response Messages
	String MSG_CONTACT_ADDED = "Contact added successfully";
	String MSG_ADDRESS_ADDED = "Address added successfully";
	
	//Tracker codes
	//Response Codes
	int USER_CREATED = 21;
	int LOCATION_UPDATED = 22;
	int LOCATION_LIST = 23;
	int USER_LIST = 24;
	int USER_LOGIN = 25;
	int LOACTIONS_DELETED = 26;
	int DEVICE_REGISTERED = 27;
	int DEVICE_LIST = 28;
	int DEVICES_DELETED = 29;
	int SEND_NOTIFICATION = 30;
	int UPDATE_STATUS = 31;
	int DEVICE_ASSIGNED = 32;
	int DEVICE_RETURNED = 33;
	
	String[] usernames = {"ravindra.kambale@happiestminds.com"};
	String[] password = {"iamright@4383"};
	//Response Messages
	String MSG_USER_CREATED = "User Created Successfully";
	String MSG_LOCATION_UPDATED = "Location Updated Successfully";
	String MSG_USER_LIST = "List of registered users.";
	String MSG_LOCATION_LIST = "List of available locations.";
	String MSG_LOG_IN= "User details";
	String MSG_LOACTIONS_DELETED= "Location Deleted Successfully";
	String MSG_DEVICE_REGISTERED = "Device registered successfully.";
	String MSG_DEVICE_ASSIGNED = "Device assigned successfully.";
	String MSG_DEVICE_LIST = "List of registered devices.";
	String MSG_DEVICES_DELETED= "Devices Deleted Successfully";
	String MSG_NOTIFICATION_SENT = "Notification Sent";
	String MSG_RING_STATUS_UPDATED = "Ring status updated";
	
	//Failure responses
	String MSG_LOCATION_UPDATING_FAILED = "Failed to update your location information";
	String MSG_LOG_IN_FAILED = "Username or password is incorrent, Try again";
}
