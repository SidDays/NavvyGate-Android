package com.sidrk.travelentsearch.details.map;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sidrk.travelentsearch.R;

public class PlaceMapsFragment extends Fragment {

    private static final String TAG = "PlaceMapsFragment";

    private static final String ARG_PLACE_DETAIL = "placeDetailsJSON";

    private String placeDetailsJSON;

    public PlaceMapsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static PlaceMapsFragment newInstance(String param1) {
        PlaceMapsFragment fragment = new PlaceMapsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLACE_DETAIL, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            placeDetailsJSON = getArguments().getString(ARG_PLACE_DETAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
