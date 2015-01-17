/**
 * Copyright Happiest Minds 2014. This program is not to be copied or distributed
 * without the express written consent of Happiest Minds. No part of  this program
 * may be used for purposes other than those intended by Happiest Minds.
 *
 * File Name        : RestClient.java
 * Description		:
 * @version         : V0.1 Jun 24, 2014
 * @author          : ravindra.kambale
 *
 * History
 * -------------------------------------------------------------------------------------
 * User				Date				Changes
 * -------------------------------------------------------------------------------------
 */
package com.ivd.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.text.TextUtils;
import android.util.Log;

import com.ivd.util.AppConstants;


/**
 * The Class RestClient.
 */
public class RestClient {

	private boolean cancelled;
	/** The tag. */
	private final String tag = "RestClient";
	/**
	 * Gets the.
	 *
	 * @param request            : RestRequest
	 * @return response : RestResponse
	 */
	public final RestResponse get(final RestRequest request) {
		cancelled = false;
		RestResponse response = new RestResponse();
		// Default: set the response status code to Failure
		response.setStatusCode(RestResponse.StatusCode.FAILURE);

		if (null != request && !TextUtils.isEmpty(request.getTargetUrl())) {
			Log.i(tag, "Executing GET request with URL: " + request.getTargetUrl() + ", Query String: "
					+ request.getQueryString());

			response = connect(request);
		} else {
			Log.e(tag, "Skipped executing incomplete GET request with URL: " + request.getTargetUrl()
					+ ", Query String: " + request.getQueryString());
		}

		return response;
	}

	/**
	 * Post.
	 *
	 * @param request            : RestRequest
	 * @return response : RestResponse
	 */
	public final RestResponse post(final RestRequest request) {
		cancelled = false;
		RestResponse response = new RestResponse();
		// Default: set the response status code to Failure
		response.setStatusCode(RestResponse.StatusCode.FAILURE);

		if (null != request && !TextUtils.isEmpty(request.getPayload())) {
			Log.i(tag, "Executing POST request with URL: " + request.getTargetUrl() + ", Payload: "
					+ request.getPayload());

			response = connect(request);

		} else {
			Log.e(tag, "Skipped executing incomplete POST request with URL: " + request.getTargetUrl() + ", Payload: "
					+ request.getPayload());
		}

		return response;
	}

	private RestResponse connect(RestRequest restRequest){
		RestResponse restResponse = new RestResponse();
		// Default: set the response status code to Failure
		restResponse.setStatusCode(RestResponse.StatusCode.FAILURE);
		String inputLine;
		HttpURLConnection request = null;
		StringBuilder response = new StringBuilder();
		try {
			if(cancelled){
				return restResponse;
			}
			byte[] dataToWrite = restRequest.getPayload().getBytes();
			URL url = new URL(restRequest.getTargetUrl());
			request = (HttpURLConnection) url.openConnection();

			if(cancelled){
				return restResponse;
			}
			request.setUseCaches(false);
			request.setDoOutput(true);
			request.setDoInput(true);

			request.setRequestMethod(AppConstants.METHOD_TYPE_POST);
			request.setConnectTimeout(AppConstants.TIME_OUT);
			request.setReadTimeout(AppConstants.TIME_OUT);

			if(restRequest.getContentType() != null){
				request.setRequestProperty(AppConstants.CONTENT_TYPE, restRequest.getContentType());
			}

			request.setRequestProperty(AppConstants.CONTENT_LENGTH, "" + dataToWrite.length);
			if(cancelled){
				return restResponse;
			}
			DataOutputStream wr = new DataOutputStream (
					request.getOutputStream ());
			wr.writeBytes (restRequest.getPayload());
			wr.flush ();
			wr.close ();

			if(cancelled){
				return restResponse;
			}
			request.connect();

			if(cancelled){
				return restResponse;
			}
			int respCode = request.getResponseCode();
			if(cancelled){
				return restResponse;
			}

			Log.i(tag, "RESP Code: " + respCode);
			if(respCode == HttpsURLConnection.HTTP_OK){
				BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				restResponse.setContent(response.toString());
				restResponse.setStatusCode(RestResponse.StatusCode.SUCCESS);
			} else {
				Log.e(tag, "RESP Code: " + respCode + " Resp Messsage:" + request.getResponseMessage());

				BufferedReader in = new BufferedReader(new InputStreamReader(request.getErrorStream()));

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				restResponse.setContent(response.toString());
			}
		} catch (IOException e) {
			Log.e(tag, "Skipped executing incomplete POST request with URL: " + restRequest.getTargetUrl() + ", Payload: "
					+ restRequest.getPayload());
			restResponse.setContent(AppConstants.SERVER_NOT_RESPONDING);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(tag, "Skipped executing incomplete POST request with URL: " + restRequest.getTargetUrl() + ", Payload: "
					+ restRequest.getPayload());
			restResponse.setContent(AppConstants.SERVER_NOT_RESPONDING);
		}finally {
			try {
				if(request != null)
				request.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return restResponse;
	}

	public void cancel(){
		cancelled = true;
	}
}
