package com.sidrk.travelentsearch;

public class Constants {

    public static final String AWSHOST = "sidtravelent-env.us-west-1.elasticbeanstalk.com";
    public static final String LOCALHOST = "localhost:8081";
    public static final String URL_HOST = "http://" + AWSHOST;

    public static final String URL_NEARBY_SEARCH = URL_HOST + "/nearbysearch";
    public static final String URL_GEOCODE = URL_HOST + "/geocode";
    public static final String URL_PLACE_DETAILS = URL_HOST + "/placedetails";
    public static final String URL_YELP = URL_HOST + "/yelpmatchreviews";
}
