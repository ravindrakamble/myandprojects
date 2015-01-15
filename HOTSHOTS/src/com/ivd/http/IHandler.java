/**
* Copyright Happiest Minds 2014. This program is not to be copied or distributed
* without the express written consent of Happiest Minds. No part of  this program
* may be used for purposes other than those intended by Happiest Minds.
*
* File Name			: Handler.java
* Description		: This is an interface
* @version         	: V0.1 Jun 24, 2014
* @author          	: ravindra.kambale
* History
* -------------------------------------------------------------------------------------
* User				Date				Changes
* -------------------------------------------------------------------------------------
*/
package com.ivd.http;

import java.lang.reflect.Type;


/**
 * The Interface Handler.
 */
public interface IHandler {

    /**
     * Send request.
     *
     * @param uiUpdator the ui updator
     * @param requestCode the request code
     * @param inputData the input data
     * @param type the type
     */
    void sendRequest(UiUpdator uiUpdator, int requestCode, RequestCreator request, Type type);

    /**
     * On response.
     *
     * @param requestCode the request code
     * @param responseCode the response code
     * @param response the response
     */
    void onResponse(int requestCode, RestResponse.StatusCode statusCode, int responseCode, RestResponse restResponse);
}
