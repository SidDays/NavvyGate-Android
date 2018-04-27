package com.sidrk.travelentsearch.details.info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.sidrk.travelentsearch.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceInfoFragment extends Fragment {

    private static final String TAG = "PlaceInfoFragment";

    private static final String ARG_PLACE_DETAIL = "placeDetailsJSON";

    private String placeDetailsJSON;

    private TableRow tableRowAddress, tableRowPhone, tableRowPriceLevel, tableRowRating, tableRowGPage, tableRowWebsite;
    private TextView textViewPhone, textViewAddress, textViewPriceLevel, textViewGPage, textViewWebsite;
    private RatingBar ratingBarRating;

    public PlaceInfoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceInfoFragment newInstance(String json) {
        PlaceInfoFragment fragment = new PlaceInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLACE_DETAIL, json);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_place_info, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Connect all UI components
        tableRowAddress = (TableRow) view.findViewById(R.id.tableRowPlaceAddress);
        tableRowPhone = (TableRow) view.findViewById(R.id.tableRowPlacePhone);
        tableRowPriceLevel = (TableRow) view.findViewById(R.id.tableRowPlacePrice);
        tableRowRating = (TableRow) view.findViewById(R.id.tableRowPlaceRating);
        tableRowWebsite = (TableRow) view.findViewById(R.id.tableRowPlaceWebsite);
        tableRowGPage = (TableRow) view.findViewById(R.id.tableRowPlaceGPage);

        textViewAddress = (TextView) view.findViewById(R.id.textViewPlaceAddress);
        textViewPhone = (TextView) view.findViewById(R.id.textViewPlacePhone);
        textViewPriceLevel = (TextView) view.findViewById(R.id.textViewPlacePrice);
        ratingBarRating = (RatingBar) view.findViewById(R.id.ratingBarPlaceRating);
        textViewGPage = (TextView) view.findViewById(R.id.textViewPlaceGPage);
        textViewWebsite = (TextView) view.findViewById(R.id.textViewPlaceWebsite);

        // fill in fragment data
        placeDetailsJSON = getArguments().getString(ARG_PLACE_DETAIL);
        try {
            populateFragmentWithJSON(placeDetailsJSON);
        } catch (JSONException e) {
            Log.e(TAG, "Invalid response!");
            e.printStackTrace();
        }
    }

    private void populateFragmentWithJSON(String responseString) throws JSONException {

        JSONObject json = new JSONObject(responseString);
        JSONObject result = json.getJSONObject("result");

        // address
        try {
            String address = result.getString("formatted_address");
            tableRowAddress.setVisibility(View.VISIBLE);
            textViewAddress.setText(address);
        } catch (JSONException e) {
            tableRowAddress.setVisibility(View.GONE);
        }

        // phone
        try {
            String phone = result.getString("international_phone_number");
            tableRowPhone.setVisibility(View.VISIBLE);
            textViewPhone.setText(phone);
            Linkify.addLinks(textViewPhone, Linkify.PHONE_NUMBERS);
        } catch (JSONException e) {
            tableRowPhone.setVisibility(View.GONE);
        }

        // Price Level
        try {
            int priceLevel = result.getInt("price_level");
            StringBuilder priceLevelSign = new StringBuilder();
            for(int i = 0; i < priceLevel; i++) {
                priceLevelSign.append("$");
            }
            tableRowPriceLevel.setVisibility(View.VISIBLE);
            textViewPriceLevel.setText(priceLevelSign.toString());
        } catch (JSONException e) {
            tableRowPriceLevel.setVisibility(View.GONE);
        }

        // Rating
        try {
            double rating = result.getDouble("rating");
            tableRowRating.setVisibility(View.VISIBLE);
            ratingBarRating.setNumStars(5);
            ratingBarRating.setRating((float)rating);
        } catch (JSONException e) {
            tableRowRating.setVisibility(View.GONE);
        }

        // gpage
        try {
            String gpage = result.getString("url");
            tableRowGPage.setVisibility(View.VISIBLE);
            textViewGPage.setText(gpage);
            Linkify.addLinks(textViewGPage, Linkify.WEB_URLS);
        } catch (JSONException e) {
            tableRowGPage.setVisibility(View.GONE);
        }

        // Website
        try {
            String website = result.getString("website");
            tableRowWebsite.setVisibility(View.VISIBLE);
            textViewWebsite.setText(website);
            Linkify.addLinks(textViewWebsite, Linkify.WEB_URLS);
        } catch (JSONException e) {
            tableRowWebsite.setVisibility(View.GONE);
        }


    }
}