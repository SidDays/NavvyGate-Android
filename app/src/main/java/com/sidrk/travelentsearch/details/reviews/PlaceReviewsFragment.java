package com.sidrk.travelentsearch.details.reviews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.sidrk.travelentsearch.R;

public class PlaceReviewsFragment extends Fragment {

    private static final String TAG = "PlaceReviewsFragment";

    private static final String ARG_PLACE_DETAIL = "placeDetailsJSON";
    private static final String ARG_YELP = "yelpJSON";

    private String placeDetailsJSON;
    private String yelpJSON;

    private Spinner spinnerReviewSource, spinnerReviewOrder;
    private RecyclerView recyclerViewReviews;

    private RecyclerView.Adapter adapter;
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

        spinnerReviewOrder = view.findViewById(R.id.spinnerReviewOrder);
        spinnerReviewSource = view.findViewById(R.id.spinnerReviewSource);

        recyclerViewReviews = view.findViewById(R.id.recyclerViewResults);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewReviews.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewReviews.setLayoutManager(layoutManager);

        // TODO: Adapter
    }
}
