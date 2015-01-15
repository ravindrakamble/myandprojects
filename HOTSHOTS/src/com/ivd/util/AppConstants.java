package com.ivd.util;

public interface AppConstants {

	String ERROR_CODE = "error";
	String METHOD_TYPE_GET = "GET";
	String METHOD_TYPE_POST = "POST";
	String SERVER_NOT_RESPONDING = "Server Error";
	String INVALID_REQUEST = "400";
	String CONTENT_TYPE = "Content-Type";

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

}
