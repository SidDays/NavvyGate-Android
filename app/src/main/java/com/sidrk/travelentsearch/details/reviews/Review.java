package com.sidrk.travelentsearch.details.reviews;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Review {
    public int naturalOrder;
    public String authorName;
    public String reviewURL;
    public String authorProfilePicURL;
    public float rating;
    public String date;
    public long timeStamp;
    public String reviewText;

    public static String timestampToString(long timeStamp) {
        Date date = new java.util.Date(timeStamp*1000L); // convert seconds to milliseconds
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * Default constructor
     */
    public Review() {
        this.naturalOrder = 0;
        this.authorName = "John Doe";
        this.reviewURL = "http://www.google.com/";
        this.rating = 2.5f;
        this.timeStamp = 0;
        this.date = timestampToString(this.timeStamp);
        this.reviewText = "The quick brown fox jumped over the lazy dog. Jackdaws love my red phoenix of quartz. Blah dee-blah!";
    }

    public Review(int naturalOrder, String authorName, float rating, long timeStamp, String reviewText, String authorProfilePicURL, String reviewURL) {

        this.naturalOrder = naturalOrder;
        this.authorName = authorName;
        this.rating = rating;
        this.timeStamp = timeStamp;
        this.date = timestampToString(this.timeStamp);
        this.reviewText = reviewText;
        this.authorProfilePicURL = authorProfilePicURL;
        this.reviewURL = reviewURL;

    }
}
