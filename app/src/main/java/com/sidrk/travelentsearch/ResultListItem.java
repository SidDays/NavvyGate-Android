package com.sidrk.travelentsearch;
import com.sidrk.travelentsearch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultListItem {

    private String name, address;
    private int iconId;

    public String getPlaceId() {
        return placeId;
    }

    private String placeId;
    private int favoriteStatusId;

    public String getIconURL() {
        return iconURL;
    }

    private String iconURL;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getIconId() {
        return iconId;
    }

    public int getFavoriteStatusId() {
        return favoriteStatusId;
    }


    public ResultListItem() {
        this.placeId = "";
        this.name = "Name unspecified";
        this.address = "Address unspecified";
        this.iconId = R.drawable.place_icon_generic;
        this.favoriteStatusId = R.drawable.heart_outline_black;
    }

    public ResultListItem(String placeid, String name, String address, String iconUrl, int favoriteStatusId) {
        this.placeId = placeid;
        this.name = name;
        this.address = address;
        this.iconURL = iconUrl;
        this.favoriteStatusId = favoriteStatusId;
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
        String placeid = result.getString("place_id");

        ResultListItem item = new ResultListItem(placeid, name, address, iconUrl, R.drawable.heart_outline_black);

        return item;
    }
}