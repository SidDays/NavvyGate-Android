package com.sidrk.travelentsearch.details.photos;

import android.content.Context;
import android.graphics.Bitmap;
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

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sidrk.travelentsearch.R;

public class PlacePhotosFragment extends Fragment {

    private static final String TAG = "PlacePhotosFragment";

    private static final String ARG_PLACE_DETAIL = "placeDetailsJSON";

    private String placeDetailsJSON;
    private String placeId = "ChIJa147K9HX3IAR-lwiGIQv9i4";

    private RecyclerView recyclerViewPhotos;
    private RecyclerView.Adapter photosAdapter;
    private RecyclerView.LayoutManager photosLayoutManager;

    // Maps stuff
    protected GeoDataClient mGeoDataClient;

    public PlacePhotosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static PlacePhotosFragment newInstance(String param1) {
        PlacePhotosFragment fragment = new PlacePhotosFragment();
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
        return inflater.inflate(R.layout.fragment_place_photos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewPhotos = (RecyclerView) view.findViewById(R.id.recyclerViewPhotos);
        recyclerViewPhotos.setHasFixedSize(true);
        photosLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewPhotos.setLayoutManager(photosLayoutManager);

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(getActivity().getApplicationContext());

        // Start using the Places API.
        getPhotos();
    }

    // Request photos and metadata for the specified place.
    private void getPhotos() {

        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);

        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();

                photosAdapter = new PhotosAdapter(photoMetadataBuffer, mGeoDataClient);
                recyclerViewPhotos.setAdapter(photosAdapter);

            }
        });
    }

}
