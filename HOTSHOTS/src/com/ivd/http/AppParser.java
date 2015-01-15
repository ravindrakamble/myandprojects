/**
 * Copyright Happiest Minds 2014. This program is not to be copied or distributed
 * without the express written consent of Happiest Minds. No part of  this program
 * may be used for purposes other than those intended by Happiest Minds.
 *
 * File Name        : AppParser.java
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

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ivd.http.models.ApiErrorResponse;
import com.ivd.util.AppConstants;


/**
 * The Class AppParser.
 */
public class AppParser {

	private final String TAG = "AppParser";
	/**
	 * Parses the.
	 *
	 * @param jsonString the json string
	 * @param type the type
	 * @return the type
	 */
	public final Type parse(String jsonString, final Type type) {
		Log.i(TAG, "Type: " + type + " JSON Response:" + jsonString);
		Gson gson = new Gson();

		Type response = null;
		try {
			if(jsonString.indexOf(AppConstants.ERROR_CODE) == -1) {
				/*if(EAppConstants.ENCRYPTION_VALUE){

					StringBuilder encResp = new StringBuilder();
					String[] encData = jsonString.split(EAppConstants.COLON_NO_SPACE);
					String str = null;
					for(int i = 0; i < encData.length; i++){
						str = encData[i];
						if(str.contains("data")){
							Encryption enc = new Encryption();
							String str2 = encData[i + 1].trim().substring(1, encData[i + 1].length() - 2);
							encResp.append(str + EAppConstants.COLON_NO_SPACE);
							encResp.append(enc.decrypt(str2).trim());
							encResp.append("}");
							i++;
						}else {
							encResp.append(str);
						}
					}

					response = gson.fromJson(encResp.toString(), type);
				} else {*/
					response = gson.fromJson(jsonString, type);
				//}
			} else {
				Type errorType = new TypeToken<ApiErrorResponse>(){}.getType();
				response = gson.fromJson(jsonString, errorType);
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}


		return response;

	}
}
