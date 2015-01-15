/**
* Copyright Happiest Minds 2014. This program is not to be copied or distributed
* without the express written consent of Happiest Minds. No part of  this program
* may be used for purposes other than those intended by Happiest Minds.
*
* File Name        : HttpTask.java
* Description		:
* @version         : V0.1 Jun 27, 2014
* @author          : ravindra.kambale
*
* History
* -------------------------------------------------------------------------------------
* User				Date				Changes
* -------------------------------------------------------------------------------------
*/
package com.ivd.http;

import java.lang.reflect.Type;

import com.ivd.util.AppConstants;


/**
 * The Class HttpTask.
 */
public class HttpTask implements Runnable {

	/** The request code. */
	private final int requestCode;

	/** The rest request. */
	private final RestRequest restRequest;

	/** The output class. */
	private final Type outputClass;

	/** The handler. */
	private final IHandler handler;

	/** The rest client. */
	private RestClient restClient;

	/** The rest response. */
	private RestResponse restResponse;


	/**
	 * Instantiates a new http task.
	 *
	 * @param mHandler the handler
	 * @param reqCode the req code
	 * @param rRequest the r request
	 * @param oClass the o class
	 */
	public HttpTask(final IHandler mHandler, final int reqCode, final RestRequest rRequest, final Type oClass) {
		this.requestCode = reqCode;
		this.restRequest = rRequest;
		this.outputClass = oClass;
		this.handler = mHandler;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public final void run() {
		restClient = new RestClient();
		boolean flag = false;
		try {
			if (restRequest.getMethodType().equalsIgnoreCase(AppConstants.METHOD_TYPE_GET)) {
				restResponse = restClient.get(restRequest);
			} else {
				/*if(EAppConstants.ENCRYPTION_VALUE){
					Encryption encryption = new Encryption();
					restRequest.setPayload(new String(encryption.encrypt(restRequest.getPayload())));
				}*/
				restResponse = restClient.post(restRequest);
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag) {
			restResponse.setOutput(outputClass);
			handler.onResponse(requestCode, RestResponse.StatusCode.SUCCESS, AppConstants.RESPONSE_OK, restResponse);
		} else {
			int respCode = AppConstants.RESPONSE_REQUEST_FAILED;
			if(restResponse != null){
				if(restResponse.getContent() != null){
					if(restResponse.getContent().equalsIgnoreCase(AppConstants.SERVER_NOT_RESPONDING)){
						respCode = AppConstants.SERVER_ERROR;
					} else if (restResponse.getContent().equalsIgnoreCase(AppConstants.INVALID_REQUEST)){
						respCode = AppConstants.CLIENT_ERROR;
					}
				}
			}
			handler.onResponse(requestCode, RestResponse.StatusCode.FAILURE, respCode, restResponse);
		}
	}


}
