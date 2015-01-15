/**
* Copyright Happiest Minds 2014. This program is not to be copied or distributed
* without the express written consent of Happiest Minds. No part of  this program
* may be used for purposes other than those intended by Happiest Minds.
*
* File Name        : EConnectionManager.java
* Description		: EConnectionManager class used to check Network availability  
* @version         : V0.1 Jul 8, 2014
* @author          : sathishkumar.t
* 
* History
* -------------------------------------------------------------------------------------
* User				Date				Changes	
* -------------------------------------------------------------------------------------
* 
*/
package com.ivd.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * The Class EConnectionManager.
 */
public final class ConnectionManager {

	/** The connectivity manager. */
	private ConnectivityManager connectivityManager = null;
	/** The instance. */
	private static ConnectionManager instance = new ConnectionManager();

	/**
	 * Instantiates a new connection manager.
	 */
	private ConnectionManager() {
	}

	/**
	 * Gets the single instance of ConnectionManager.
	 *
	 * @return single instance of ConnectionManager
	 */
	public static ConnectionManager getInstance() {
		return instance;
	}

	/**
	 * Method used to check network availability, if available return true else
	 * return false.
	 *
	 * @param mContext the m context
	 * @return true, if is online
	 */
	public boolean isOnline(final Context mContext) {
		boolean netAvailable = false;
		if (connectivityManager == null) {
			connectivityManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			netAvailable = true;
		}
		return netAvailable;
	}

}
