package com.sidrk.travelentsearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.sidrk.travelentsearch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultListItem implements Parcelable{

    // TODO: implement parcel creator etc
    // https://developer.android.com/reference/android/os/Parcelable.html

    private String name, address;
    int iconId;
    int favoriteStatusId;

    ResultListItem() {
        this.name = "Name unspecified";
        this.address = "Address unspecified";
        this.iconId = R.drawable.place_icon_unspecified;
        this.favoriteStatusId = R.drawable.heart_fill_blue;
    }

    ResultListItem(String name, String address, int iconId, int favoriteStatusId) {
        this.name = name;
        this.address = address;
        this.iconId = iconId;
        this.favoriteStatusId = favoriteStatusId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static ResultListItem[] parseResponse(String responseString) throws JSONException {

        ResultListItem[] array = null;

        JSONObject json = new JSONObject(responseString);
        JSONArray results = json.getJSONArray("results");

        array = new ResultListItem[results.length()];
        for(int result_i = 0; result_i < results.length(); result_i++) {
            JSONObject result = results.getJSONObject(result_i);
            array[result_i] = parseResultObject(result);
        }

        return array;
    }

    public static ResultListItem parseResultObject(JSONObject result) throws JSONException {

        // TODO
        String name = result.getString("name");
        String address = result.getString("vicinity");
        String iconUrl = result.getString("icon");

        ResultListItem item = new ResultListItem(name, address, R.drawable.place_icon_unspecified, R.drawable.heart_fill_white);

        return item;
    }
}