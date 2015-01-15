/**
 * Copyright Happiest Minds 2014. This program is not to be copied or distributed
 * without the express written consent of Happiest Minds. No part of  this program
 * may be used for purposes other than those intended by Happiest Minds.
 *
 * File Name        : RestController.java
 * Description		: This class in controller to execute the requests
 * @version         : V0.1 Jun 24, 2014
 * @author          : ravindra.kambale
 *
 * History
 * -------------------------------------------------------------------------------------
 * User				Date				Changes
 * -------------------------------------------------------------------------------------
 */
package com.ivd.http;

import java.lang.reflect.Type;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.ivd.http.RestResponse.StatusCode;
import com.ivd.http.models.ApiErrorResponse;
import com.ivd.util.AppConstants;

/**
 * The Class RestController.
 */
public class RestController implements IHandler {

	private final static String TAG = "ERestController";
	private final static RestController controller = new RestController();

	/** The json parser. */
	private AppParser jsonParser;

	private AsyncTaskExecutor taskAsync;

	private UiUpdator uiUpdator;

	private int mRequestCode;
	private RestRequest mRequest;

	private boolean reqIncomplete;

	private Type output;

	private RestController() {

	}

	public static RestController getInstance(){
		return controller;
	}

	/* (non-Javadoc)
	 * @see com.hm.sampleproject.comp.Handler#sendRequest(int, java.lang.Object, java.lang.Class)
	 */
	@Override
	public final void sendRequest(UiUpdator uiUpdator, final int requestCode, RequestCreator request, final Type outputData) {
		Log.i(TAG, "sendRequest");
		reqIncomplete = false;
		this.uiUpdator = uiUpdator;
		mRequestCode = requestCode;
		output = outputData;
		if(ConnectionManager.getInstance().isOnline(getContext(uiUpdator))){
			if(outputData instanceof Type) {
				mRequest = request.createRequest();
				taskAsync = new AsyncTaskExecutor(this,  requestCode, outputData);
				taskAsync.execute(mRequest);

			} else {
				Log.i(TAG, "Output class provided is not an instance of Type. add <implements Type> to your class.");
				onResponse(requestCode, StatusCode.FAILURE, AppConstants.RESPONSE_OUTPUT_OBJECT_NOT_PROPER, null);
			}
		}else{
			onResponse(requestCode, StatusCode.FAILURE, AppConstants.RESPONSE_NO_NETWORK, null);
		}
	}

	@Override
	public void onResponse(final int requestCode, final StatusCode statusCode,
			final int responseCode, final RestResponse restResponse) {
		if (statusCode == StatusCode.SUCCESS) {
			jsonParser = new AppParser();
			Type type = jsonParser.parse(restResponse.getContent(), restResponse.getOutput());
			uiUpdator.updateUI(requestCode, statusCode, responseCode, type);
		} else {
			jsonParser = new AppParser();
			Type type = jsonParser.parse(restResponse.getContent(), null);
			uiUpdator.updateUI(requestCode, statusCode, responseCode, type);
		}

		Log.i(TAG, "onResponse Completed");
	}


	private Context getContext(UiUpdator uiUpdator){
		Context context = null;

		if(uiUpdator instanceof Activity) {
			context = (Activity)uiUpdator;
		}else {
			Fragment frag = (Fragment)uiUpdator;
			context = frag.getActivity();
		}
		return context;
	}

}
