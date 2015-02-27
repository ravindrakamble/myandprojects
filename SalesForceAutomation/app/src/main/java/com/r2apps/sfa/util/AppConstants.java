package com.r2apps.sfa.util;

/**
 * Created by ravindra.kambale on 2/25/2015.
 */
public interface AppConstants {
    int DASHBOARD = 0;
    int DISTRIBUTOR = 1;
    int RETAILERS = 2;
    int PREFERENCES = 3;
    int ORDERS = 4;
    int ADD_RETAILER = 5;
    int STORES = 6;

    int DISTANCE_IN_METERS = 5000;
    String LOCATION = "12.8897331,77.5780595";
    String API_KEY = "AIzaSyAIpkRMJlyDbUuklzrcMX9jq_PWW91lBBk";
    String PLACES_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
            + LOCATION
            + "&radius=" + DISTANCE_IN_METERS
            + "&types=grocery_or_supermarket" +
            "&key=" + API_KEY;
}
