package com.sidrk.travelentsearch;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerViewFavorites;
    private TextView textViewFavoritesEmpty;
    public static FavoritesAdapter mAdapter = new FavoritesAdapter();
    private RecyclerView.LayoutManager layoutManager;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FavoritesFragment.
     */
    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        // args.putString(ARG_PARAM1, param1);
        // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        if (getArguments() != null) {
        //            mParam1 = getArguments().getString(ARG_PARAM1);
        //            mParam2 = getArguments().getString(ARG_PARAM2);
        //        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewFavoritesEmpty = view.findViewById(R.id.textViewFavoritesEmpty);

        if(FavoritesFragment.mAdapter.getItemCount() == 0) {
            textViewFavoritesEmpty.setVisibility(View.VISIBLE);
        }
        else {
            textViewFavoritesEmpty.setVisibility(View.GONE);
        }

        recyclerViewFavorites = view.findViewById(R.id.recyclerViewFavorites);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewFavorites.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewFavorites.setLayoutManager(layoutManager);

        recyclerViewFavorites.setAdapter(mAdapter);
    }
}
