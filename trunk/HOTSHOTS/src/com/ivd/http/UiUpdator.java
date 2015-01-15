package com.ivd.http;

import java.lang.reflect.Type;


/**
 * The Interface UiUpdator.
 */
public interface UiUpdator {

	/**
	 * This method is used as a callback to update the UI.
	 *
	 * @param requestCode the request code
	 * @param responseCode the response code
	 * @param data the data
	 */
	void updateUI(int requestCode, RestResponse.StatusCode statusCode, int responseCode, Type data);
}
