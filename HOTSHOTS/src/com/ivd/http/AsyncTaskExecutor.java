
package com.ivd.http;

import java.lang.reflect.Type;

import android.os.AsyncTask;

import com.ivd.http.RestResponse.StatusCode;
import com.ivd.util.AppConstants;

/**
 * The Class EAsyncTask.
 */
public class AsyncTaskExecutor extends AsyncTask<RestRequest, Void, RestResponse> {

	/** The request code. */
	private final int requestCode;

	/** The output class. */
	private final Type outputClass;

	/** The handler. */
	private final IHandler handler;

	RestClient restClient;

	public AsyncTaskExecutor(final IHandler mHandler, final int reqCode, final Type oClass) {
		this.requestCode = reqCode;
		this.outputClass = oClass;
		this.handler = mHandler;
	}

	@Override
	protected RestResponse doInBackground(RestRequest... params) {
		RestRequest restRequest = params[0];
		restClient = new RestClient();
		RestResponse restResponse = null;
		try {
			if(!isCancelled()){
				if (restRequest.getMethodType().equalsIgnoreCase(AppConstants.METHOD_TYPE_GET)) {
					restResponse = restClient.get(restRequest);
				} else {
					restResponse = restClient.post(restRequest);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return restResponse;
	}

	@Override
	protected void onPostExecute(RestResponse result) {
		if(!isCancelled()){
			if (result != null && result.getStatusCode() == StatusCode.SUCCESS) {
				result.setOutput(outputClass);
				handler.onResponse(requestCode, RestResponse.StatusCode.SUCCESS, AppConstants.RESPONSE_OK, result);
			} else {
				int respCode = AppConstants.RESPONSE_REQUEST_FAILED;
				if(result != null){
					if(result.getContent() != null){
						if(result.getContent().equalsIgnoreCase(AppConstants.SERVER_NOT_RESPONDING)){
							respCode = AppConstants.SERVER_ERROR;
						} else if (result.getContent().equalsIgnoreCase(AppConstants.INVALID_REQUEST)){
							respCode = AppConstants.CLIENT_ERROR;
						}
					}
				}
				handler.onResponse(requestCode, RestResponse.StatusCode.FAILURE, respCode, result);
			}
		}
	}

	public void cancel(){
		if(restClient != null){
			restClient.cancel();
		}
	}

}
