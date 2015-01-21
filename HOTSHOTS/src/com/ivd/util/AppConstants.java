package com.ivd.util;

public interface AppConstants {

	/**
	 * URL and METHODS
	 */
	String URL = "http://demo.ivdisplays.net/hotshots/webservices/api.php";
	String METHOD_REGISTRATION = "register";
	String METHOD_CONTACT_SYNC = "contactsync";
	String METHOD_CONTACT_LIST = "contactlist";
	String METHOD_UPLOAD_HOTSHOT = "uploadhotshot";
	String METHOD_UPDATE_MESSAGE = "updatemessage";
	String METHOD_MESSAGE_LIST = "messagelist";

	String IVD_PREF = "IVDPREF";
	String KEY_USER_ID = "userID";
	String KEY_USER_MOBILE = "mobile";
	String KEY_USER_EMAIL = "email";
	String MESSAGE = "Please Wait...";


	String ERROR_CODE = "error";
	String METHOD_TYPE_GET = "GET";
	String METHOD_TYPE_POST = "POST";
	String SERVER_NOT_RESPONDING = "Server Error";
	String INVALID_REQUEST = "400";
	String CONTENT_TYPE = "Content-Type";
	String CONTENT_LENGTH = "Content-Length";
	String CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";

	/**
	 * Response Codes
	 */
	int RESPONSE_OK = 200;
	int RESPONSE_REQUEST_FAILED = 30;
	int RESPONSE_GCM = 31;
	int RESPONSE_OUTPUT_OBJECT_NOT_PROPER = 32;
	int RESPONSE_NO_NETWORK = 33;
	int SERVER_ERROR = 550;
	int CLIENT_ERROR = 400;
	int TIME_OUT = 60 * 1000;

	/**
	 * Request Codes
	 */
	int REQUEST_REGISTRATION = 11;
	int REQUEST_CONTACT_SYNC = 12;
	int REQUEST_CONTACT_LIST = 13;
	int REQUEST_UPLOAD_HOTSHOT = 14;
	int REQUEST_UPDATE_MESSAGE = 15;
	int REQUEST_MESSAGE_LIST = 16;

	/**
	 * Other constants
	 */
	String DEVICE_TYPE = "ANDROID";
}
