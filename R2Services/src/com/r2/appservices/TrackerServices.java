package com.r2.appservices;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.r2.appservices.dao.ApiResponse;
import com.r2.appservices.dao.Device;
import com.r2.appservices.dao.LoginData;
import com.r2.appservices.dao.TrackerDAO;
import com.r2.appservices.dao.User;
import com.r2.appservices.dao.UserDetails;
import com.r2.appservices.dao.UserLocation;
import com.r2.appservices.utils.Constants;

@Api(name = "tracker")
public class TrackerServices {

	Logger logger = Logger.getLogger("Tracker");
	
	@ApiMethod(
			name = "addUser",
			path = "addUser",
			httpMethod = HttpMethod.POST
			)
	public ApiResponse addUser(User user){
		TrackerDAO.INSTANCE.add(user);

		String id = String.valueOf(user.getId());
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setResponseCode(Constants.USER_CREATED);
		apiResponse.setResponseMessage(Constants.MSG_USER_CREATED);
		apiResponse.setResponseStatus(Constants.SUCCESS);
		apiResponse.setResponseBody(id);

		return apiResponse;
	}


	@ApiMethod(
			name = "registerDevice",
			path = "registerDevice",
			httpMethod = HttpMethod.POST
			)
	public ApiResponse registerDevice(Device device){
		TrackerDAO.INSTANCE.add(device);

		String id = String.valueOf(device.getId());
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setResponseCode(Constants.DEVICE_REGISTERED);
		apiResponse.setResponseMessage(Constants.MSG_DEVICE_REGISTERED);
		apiResponse.setResponseStatus(Constants.SUCCESS);
		apiResponse.setResponseBody(id);

		return apiResponse;
	}
	
	@ApiMethod(
			name = "assignDevice",
			path = "assignDevice",
			httpMethod = HttpMethod.POST
			)
	public ApiResponse assignDevice(UserDetails userDetails){
		TrackerDAO.INSTANCE.add(userDetails);

		String id = String.valueOf(userDetails.getId());
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setResponseCode(Constants.DEVICE_ASSIGNED);
		apiResponse.setResponseMessage(Constants.MSG_DEVICE_ASSIGNED);
		apiResponse.setResponseStatus(Constants.SUCCESS);
		apiResponse.setResponseBody(id);

		return apiResponse;
	}
	
	@ApiMethod(
			name = "returnDevice",
			path = "returnDevice",
			httpMethod = HttpMethod.POST
			)
	public ApiResponse returnDevice(UserDetails userDetails){
		TrackerDAO.INSTANCE.add(userDetails);

		String id = String.valueOf(userDetails.getId());
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setResponseCode(Constants.DEVICE_ASSIGNED);
		apiResponse.setResponseMessage(Constants.MSG_DEVICE_ASSIGNED);
		apiResponse.setResponseStatus(Constants.SUCCESS);
		apiResponse.setResponseBody(id);

		return apiResponse;
	}
	@ApiMethod(
			name = "login",
			path = "login",
			httpMethod = HttpMethod.POST
			)
	public ApiResponse login(User user){
		User loginUser = TrackerDAO.INSTANCE.login(user);
		ApiResponse apiResponse = new ApiResponse();
		if(loginUser != null){
			apiResponse.setResponseCode(Constants.USER_LOGIN);
			apiResponse.setResponseMessage(Constants.MSG_LOG_IN);
			apiResponse.setResponseStatus(Constants.SUCCESS);
			Gson gson = new Gson();
			Type collectionType = new TypeToken<User>(){}.getType();
			apiResponse.setResponseBody(gson.toJson(loginUser, collectionType));
		}else{
			apiResponse.setResponseCode(Constants.USER_LOGIN);
			apiResponse.setResponseMessage(Constants.MSG_LOG_IN_FAILED);
			apiResponse.setResponseStatus(Constants.FAILURE);
		}

		return apiResponse;
	}
	
	@ApiMethod(
			name = "signin",
			path = "signin",
			httpMethod = HttpMethod.POST
			)
	public ApiResponse signin(LoginData loginData){

		ApiResponse apiResponse = new ApiResponse();
		if(loginData != null){
			if(Arrays.asList(Constants.usernames).contains(loginData.getUsername()) && 
					Arrays.asList(Constants.password).contains(loginData.getPassword())){
				apiResponse.setResponseCode(Constants.USER_LOGIN);
				apiResponse.setResponseMessage(Constants.MSG_LOG_IN);
				apiResponse.setResponseStatus(Constants.SUCCESS);
			}else{
				apiResponse.setResponseCode(Constants.USER_LOGIN);
				apiResponse.setResponseMessage(Constants.MSG_LOG_IN_FAILED);
				apiResponse.setResponseStatus(Constants.FAILURE);
			}
		}else{
			apiResponse.setResponseCode(Constants.USER_LOGIN);
			apiResponse.setResponseMessage(Constants.MSG_LOG_IN_FAILED);
			apiResponse.setResponseStatus(Constants.FAILURE);
		}

		return apiResponse;
	}
	
	
	@ApiMethod(
			name = "updateLocation",
			path = "updateLocation",
			httpMethod = HttpMethod.POST
			)
	public ApiResponse updateLocation(UserLocation userLocation){
		boolean flag = false;
		try {
			TrackerDAO.INSTANCE.addOrUpdate(userLocation);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		ApiResponse apiResponse = new ApiResponse();
		if(flag){
			apiResponse.setResponseCode(Constants.LOCATION_UPDATED);
			apiResponse.setResponseMessage(Constants.MSG_LOCATION_UPDATED);
			apiResponse.setResponseStatus(Constants.SUCCESS);
			apiResponse.setResponseBody("" + TrackerDAO.INSTANCE.getDevice(String.valueOf(userLocation.getUserId())));
		}else{
			apiResponse.setResponseCode(Constants.LOCATION_UPDATED);
			apiResponse.setResponseMessage(Constants.MSG_LOCATION_UPDATING_FAILED);
			apiResponse.setResponseStatus(Constants.FAILURE);
		}
		return apiResponse;
	}

	@ApiMethod(
			name = "getUsers",
			path = "getUsers",
			httpMethod = HttpMethod.GET
			)
	public ApiResponse getUsers(){
		boolean flag = false;
		List<User> users = new ArrayList<User>();
		try {
			users = TrackerDAO.INSTANCE.listUsers();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		ApiResponse apiResponse = new ApiResponse();
		if(flag){
			apiResponse.setResponseCode(Constants.USER_LIST);
			apiResponse.setResponseMessage(Constants.MSG_USER_LIST);
			apiResponse.setResponseStatus(Constants.SUCCESS);
			Gson gson = new Gson();
			Type collectionType = new TypeToken<Collection<User>>(){}.getType();
			apiResponse.setResponseBody(gson.toJson(users, collectionType));
		}else{
			apiResponse.setResponseCode(Constants.USER_LIST);
			apiResponse.setResponseMessage(Constants.MSG_USER_LIST);
			apiResponse.setResponseStatus(Constants.FAILURE);
		}
		return apiResponse;
	}

	@ApiMethod(
			name = "getUserLocations",
			path = "getUserLocations",
			httpMethod = HttpMethod.GET
			)
	public ApiResponse getUserLocations(){
		boolean flag = false;
		List<UserLocation> users = new ArrayList<UserLocation>();
		try {
			users = TrackerDAO.INSTANCE.listCurrentLocations();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		ApiResponse apiResponse = new ApiResponse();
		if(flag){
			apiResponse.setResponseCode(Constants.LOCATION_LIST);
			apiResponse.setResponseMessage(Constants.MSG_LOCATION_LIST);
			apiResponse.setResponseStatus(Constants.SUCCESS);
			Gson gson = new Gson();
			Type collectionType = new TypeToken<Collection<UserLocation>>(){}.getType();
			apiResponse.setResponseBody(gson.toJson(users, collectionType));
		}else{
			apiResponse.setResponseCode(Constants.LOCATION_LIST);
			apiResponse.setResponseMessage(Constants.MSG_LOCATION_LIST);
			apiResponse.setResponseStatus(Constants.FAILURE);
		}
		return apiResponse;
	}

	@ApiMethod(
			name = "deleteUserLocations",
			path = "deleteUserLocations",
			httpMethod = HttpMethod.GET
			)
	public ApiResponse deleteUserLocations(){
		boolean flag = false;
		try {
			flag = TrackerDAO.INSTANCE.removeAllLocations();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		ApiResponse apiResponse = new ApiResponse();
		if(flag){
			apiResponse.setResponseCode(Constants.LOACTIONS_DELETED);
			apiResponse.setResponseMessage(Constants.MSG_LOACTIONS_DELETED);
			apiResponse.setResponseStatus(Constants.SUCCESS);
		}else{
			apiResponse.setResponseCode(Constants.LOACTIONS_DELETED);
			apiResponse.setResponseMessage(Constants.MSG_LOACTIONS_DELETED);
			apiResponse.setResponseStatus(Constants.FAILURE);
		}
		return apiResponse;
	}

	@ApiMethod(
			name = "getDevices",
			path = "getDevices",
			httpMethod = HttpMethod.GET
			)
	public ApiResponse getDevices(){
		boolean flag = false;
		List<Device> devices = new ArrayList<Device>();
		try {
			devices = TrackerDAO.INSTANCE.listDevices();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		ApiResponse apiResponse = new ApiResponse();
		if(flag){
			apiResponse.setResponseCode(Constants.DEVICE_LIST);
			apiResponse.setResponseMessage(Constants.MSG_DEVICE_LIST);
			apiResponse.setResponseStatus(Constants.SUCCESS);
			Gson gson = new Gson();
			Type collectionType = new TypeToken<Collection<Device>>(){}.getType();
			apiResponse.setResponseBody(gson.toJson(devices, collectionType));
		}else{
			apiResponse.setResponseCode(Constants.DEVICE_LIST);
			apiResponse.setResponseMessage(Constants.MSG_DEVICE_LIST);
			apiResponse.setResponseStatus(Constants.FAILURE);
		}
		return apiResponse;
	}

	@ApiMethod(
			name = "removeDevices",
			path = "removeDevices",
			httpMethod = HttpMethod.GET
			)
	public ApiResponse removeDevices(){
		boolean flag = false;
		try {
			flag = TrackerDAO.INSTANCE.removeAllDevices();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		ApiResponse apiResponse = new ApiResponse();
		if(flag){
			apiResponse.setResponseCode(Constants.DEVICES_DELETED);
			apiResponse.setResponseMessage(Constants.MSG_DEVICES_DELETED);
			apiResponse.setResponseStatus(Constants.SUCCESS);
		}else{
			apiResponse.setResponseCode(Constants.DEVICES_DELETED);
			apiResponse.setResponseMessage(Constants.MSG_DEVICES_DELETED);
			apiResponse.setResponseStatus(Constants.FAILURE);
		}
		return apiResponse;
	}

	@ApiMethod(
			name = "updateRingStatus",
			path = "updateRingStatus/{imei}/{status}",
			httpMethod = HttpMethod.GET
			)
	public ApiResponse updateRingStatus(@Named("imei") String imei, @Named("status") int status){
		boolean flag = TrackerDAO.INSTANCE.updateRingStatus(imei, status);
		ApiResponse apiResponse = new ApiResponse();
		if(flag){
			apiResponse.setResponseCode(Constants.UPDATE_STATUS);
			apiResponse.setResponseMessage(Constants.MSG_RING_STATUS_UPDATED);
			apiResponse.setResponseStatus(Constants.SUCCESS);
		}else{
			apiResponse.setResponseCode(Constants.UPDATE_STATUS);
			apiResponse.setResponseMessage(Constants.MSG_RING_STATUS_UPDATED);
			apiResponse.setResponseStatus(Constants.FAILURE);
		}
		return apiResponse;
	}
	
	@ApiMethod(
			name = "sendNotification",
			path = "sendNotification/{imei}",
			httpMethod = HttpMethod.GET
			)
	public ApiResponse sendNotification(@Named("imei") String imei){
		
		// Instance of com.android.gcm.server.Sender, that does the
		// transmission of a Message to the Google Cloud Messaging service.
		Sender sender = new Sender("AIzaSyALIqWd3F1u4lSRWgmSAP1ZZ201itggviA");
		 
		// This Message object will hold the data that is being transmitted
		// to the Android client devices.  For this demo, it is a simple text
		// string, but could certainly be a JSON object.
		Message message = new Message.Builder()
		 
		// If multiple messages are sent using the same .collapseKey()
		// the android target device, if it was offline during earlier message
		// transmissions, will only receive the latest message for that key when
		// it goes back on-line.
		.collapseKey("GCM_Message")
		.timeToLive(30)
		.delayWhileIdle(true)
		.addData("message", "Test Message")
		.build();
		List<String> androidTargets = new ArrayList<String>();
		androidTargets.add("APA91bHIo86CzWdkkuFFXOZfymd-fW51AlRZZU-xM-NTKRnLEN4n_ZzkzOtyM49I0cQl76bb4_vXwTK1mjWAjIPnys2MpVE9M1S1cBxNEATNBwWtSvK85Po2pIx0AqKfHYYUR3xwmTB4CNG6jBR2RCg37Z1GtFHaTA");
		try {
		    // use this for multicast messages.  The second parameter
		    // of sender.send() will need to be an array of register ids.
		    MulticastResult result = sender.send(message, androidTargets, 1);
		     
		    if (result.getResults() != null) {
		    	 System.out.println("Result: " + result);
		        int canonicalRegId = result.getCanonicalIds();
		        System.out.println("canonicalRegId: " + canonicalRegId);
		    } else {
		        int error = result.getFailure();
		        System.out.println("Broadcast failure: " + error);
		    }
		     
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setResponseCode(Constants.SEND_NOTIFICATION);
		apiResponse.setResponseMessage(Constants.MSG_NOTIFICATION_SENT);
		apiResponse.setResponseStatus(Constants.SUCCESS);

		return apiResponse;
	}
}
