package com.sidrk.travelentsearch.details.reviews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sidrk.travelentsearch.R;
import com.sidrk.travelentsearch.ResultListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlaceReviewsFragment extends Fragment {

    private static final String TAG = "PlaceReviewsFragment";

    private static final String ARG_PLACE_DETAIL = "placeDetailsJSON";
    private static final String ARG_YELP = "yelpJSON";

    private String placeDetailsJSON;
    private String yelpJSON;

    private Spinner spinnerReviewSource, spinnerReviewOrder;
    private RecyclerView recyclerViewReviews;
    private TextView textViewReviewsEmpty;

    private ReviewsAdapter reviewsGoogleAdapter, reviewsYelpAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public PlaceReviewsFragment() {
        // Required empty public constructor
    }

    public static PlaceReviewsFragment newInstance(String param1, String param2) {
        PlaceReviewsFragment fragment = new PlaceReviewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLACE_DETAIL, param1);
        args.putString(ARG_YELP, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            placeDetailsJSON = getArguments().getString(ARG_PLACE_DETAIL);

            yelpJSON = getArguments().getString(ARG_YELP);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_reviews, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewReviewsEmpty = view.findViewById(R.id.textViewReviewsEmpty);

        recyclerViewReviews = view.findViewById(R.id.recyclerViewReviews);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewReviews.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewReviews.setLayoutManager(layoutManager);

        reviewsGoogleAdapter = new ReviewsAdapter(prepareGoogleDataset(placeDetailsJSON));
        reviewsYelpAdapter = new ReviewsAdapter(prepareYelpDataset(yelpJSON));
        recyclerViewReviews.setAdapter(reviewsGoogleAdapter);

        spinnerReviewSource = view.findViewById(R.id.spinnerReviewSource);
        spinnerReviewSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textViewReviewsEmpty.setVisibility(View.GONE);
                switch (position) {
                    case 0:
                        recyclerViewReviews.swapAdapter(reviewsGoogleAdapter, false);
                        if(reviewsGoogleAdapter.getItemCount() == 0) {
                            textViewReviewsEmpty.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 1:
                        recyclerViewReviews.swapAdapter(reviewsYelpAdapter, false);
                        if(reviewsYelpAdapter.getItemCount() == 0) {
                            textViewReviewsEmpty.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerReviewOrder = view.findViewById(R.id.spinnerReviewOrder);
        spinnerReviewOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int reviewSource = spinnerReviewSource.getSelectedItemPosition();

                switch (reviewSource) {
                    case 0:
                        reviewsGoogleAdapter.sortOption(position);
                        break;
                    case 1:
                        reviewsYelpAdapter.sortOption(position);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private Review[] prepareGoogleDataset(String responseString) {

        Review[] array;

        try {
            JSONObject json = new JSONObject(responseString);
            JSONObject result = json.getJSONObject("result");

            JSONArray reviewsJSON = result.getJSONArray("reviews");

            array = new Review[reviewsJSON.length()];
            for(int i = 0; i < reviewsJSON.length(); i++) {
                JSONObject review = reviewsJSON.getJSONObject(i);

                String authorName = review.getString("author_name");
                String authorURL = review.getString("author_url");
                String profilePhotoURL = review.getString("profile_photo_url");
                float rating = (float)review.getDouble("rating");
                long timeStamp = review.getLong("time");
                String text = review.getString("text");

                array[i] = new Review(i, authorName, rating, timeStamp, text, profilePhotoURL, authorURL);
            }

        } catch (JSONException e) {

            Log.i(TAG, "No reviews for this place...?");
            // if reviewsJSON causes this, it has no reviews
            array = new Review[0];
        }

        return array;
    }

    private Review[] prepareYelpDataset(String responseString) {

        Review[] array;

        try {
            JSONArray reviewsJSON = new JSONArray(responseString);

            array = new Review[reviewsJSON.length()];
            for(int i = 0; i < reviewsJSON.length(); i++) {
                JSONObject review = reviewsJSON.getJSONObject(i);
                JSONObject author = review.getJSONObject("user");
                String authorName = author.getString("name");
                String profilePhotoURL = author.getString("image_url");

                String authorURL = review.getString("url");
                float rating = (float)review.getDouble("rating");
                String date = review.getString("time_created");
                String text = review.getString("text");

                array[i] = new Review(i, authorName, rating, date, text, profilePhotoURL, authorURL);
            }

        } catch (JSONException e) {

            Log.i(TAG, "No reviews for this place...?");
            // if reviewsJSON causes this, it has no reviews
            array = new Review[0];
        }

        return array;
    }
}
