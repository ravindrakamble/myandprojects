package com.ivd.http.request;

import java.lang.reflect.Field;

import com.ivd.http.RequestCreator;
import com.ivd.http.RestRequest;
import com.ivd.util.AppConstants;

public class GeneralRequest implements RequestCreator {

	private Object dataForRequest;
	private int requestID;
	StringBuilder data;
	public GeneralRequest(int reqID, Object data){
		this.requestID = reqID;
		this.dataForRequest = data;
	}
	@Override
	public RestRequest createRequest() {
		data = new StringBuilder();

		RestRequest restRequest = new RestRequest();
		switch(requestID){
		case AppConstants.REQUEST_REGISTRATION:
			restRequest.setTargetUrl(AppConstants.URL);
			data.append("action=" + AppConstants.METHOD_REGISTRATION);
			break;

		case AppConstants.REQUEST_CONTACT_SYNC:
			restRequest.setTargetUrl(AppConstants.URL);
			data.append("action=" + AppConstants.METHOD_CONTACT_SYNC);
			break;

		case AppConstants.REQUEST_CONTACT_LIST:
			restRequest.setTargetUrl(AppConstants.URL);
			data.append("action=" + AppConstants.METHOD_CONTACT_LIST);
			break;

		case AppConstants.REQUEST_UPLOAD_HOTSHOT:
			restRequest.setTargetUrl(AppConstants.URL);
			data.append("action=" + AppConstants.METHOD_UPLOAD_HOTSHOT);
			break;

		case AppConstants.REQUEST_UPDATE_MESSAGE:
			restRequest.setTargetUrl(AppConstants.URL);
			data.append("action=" + AppConstants.METHOD_UPDATE_MESSAGE);
			break;

		case AppConstants.REQUEST_MESSAGE_LIST:
			restRequest.setTargetUrl(AppConstants.URL);
			data.append("action=" + AppConstants.METHOD_MESSAGE_LIST);
			break;

		}
		restRequest.setContentType(AppConstants.CONTENT_TYPE_VALUE);
		restRequest.setMethodType(AppConstants.METHOD_TYPE_POST);
		if (dataForRequest != null) {
			restRequest.setPayload(getData(dataForRequest));
		}
		return restRequest;
	}

	private  String getData(Object object) {


		@SuppressWarnings("rawtypes")
		Class oClass = object.getClass();
		while (oClass != null) {
			Field[] fields = oClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				try {
					Object value = fields[i].get(object);
					if (value != null) {
						if (value.getClass().isPrimitive()
								|| value.getClass() == java.lang.Long.class
								|| value.getClass() == java.lang.String.class
								|| value.getClass() == java.lang.Integer.class
								|| value.getClass() == java.lang.Boolean.class) {
							if(data.length() > 0){
								data.append("&" + fields[i].getName()  + "=" + value.toString());
							}else{
								data.append(fields[i].getName()  + "=" + value.toString());
							}

						}
					}
				} catch (IllegalAccessException e) {
				}
			}
			oClass = oClass.getSuperclass();
		}

		return data.toString();
	}

}
