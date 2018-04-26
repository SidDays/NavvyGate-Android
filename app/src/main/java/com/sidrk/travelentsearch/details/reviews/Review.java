package com.sidrk.travelentsearch.details.reviews;

public class Review {
    public String authorName;
    public String reviewURL;
    public String authorProfilePicURL;
    public float rating;
    public String date;
    public long timeStamp;
    public String reviewText;

    /**
     * Default constructor
     */
    public Review() {
        this.authorName = "John Doe";
        this.reviewURL = "http://www.google.com/";
        this.rating = 2.5f;
        this.timeStamp = 0;
        this.date = "2000-01-01 00:00:00";
        this.reviewText = "The quick brown fox jumped over the lazy dog. Jackdaws love my red phoenix of quartz. Blah dee-blah!";
    }

    public Review(String authorName, float rating, long timeStamp, String reviewText, String authorProfilePicURL, String reviewURL) {

        this.authorName = authorName;
        this.rating = rating;
        this.timeStamp = timeStamp;
        // TODO: Date
        this.reviewText = reviewText;
        this.authorProfilePicURL = authorProfilePicURL;
        this.reviewURL = reviewURL;

    }
}
