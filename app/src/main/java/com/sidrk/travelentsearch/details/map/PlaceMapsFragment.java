package com.sidrk.travelentsearch.details.map;

import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;
import com.sidrk.travelentsearch.Constants;
import com.sidrk.travelentsearch.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceMapsFragment extends Fragment {

    private static final String TAG = "PlaceMapsFragment";

    private static final String[] TRAVEL_MODES = { "driving", "bicycling", "transit", "walking" };

    private static final String ARG_NAME = "name";
    private static final String ARG_LAT = "lat";
    private static final String ARG_LNG = "lng";

    private LatLng locationSource; // chosen via text field
    private String nameSource;
    private LatLng locationDestination;
    private String nameDestination;

    // Layout
    private PlacesAutocompleteTextView placesAutoCompleteTextViewMapLocation;
    private Spinner spinnerTravelMode;
    private boolean spinnerTravelModeFirstRun = true;
    private MapView mMapView;
    private GoogleMap googleMap;

    // Volley
    private RequestQueue queue;

    public PlaceMapsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static PlaceMapsFragment newInstance(String name, double lat, double lng) {
        PlaceMapsFragment fragment = new PlaceMapsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putDouble(ARG_LAT, lat);
        args.putDouble(ARG_LNG, lng);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            locationDestination = new LatLng(getArguments().getDouble(ARG_LAT), getArguments().getDouble(ARG_LNG));
            nameDestination = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_place_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For dropping a marker at a point on the Map
                googleMap.addMarker(new MarkerOptions().position(locationDestination).title(nameDestination));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(locationDestination).zoom(14).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        placesAutoCompleteTextViewMapLocation = view.findViewById(R.id.placesAutoCompleteTextViewMapLocation);
        placesAutoCompleteTextViewMapLocation.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {

                        // do something awesome with the selected place
                        String placeIdSource = place.place_id;
                        Log.d(TAG, place.place_id);

                        // TODO: ROUTE
                        final Uri.Builder builderGeocode = Uri.parse(Constants.URL_GEOCODE).buildUpon();
                        builderGeocode.appendQueryParameter("address", place.description);
                        String urlGeocode = builderGeocode.build().toString();

                        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGeocode,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String responseString) {

                                        Log.v(TAG, "Response is: " + responseString);

                                        try {
                                            JSONObject locationJSON = new JSONObject(responseString)
                                                    .getJSONArray("results")
                                                    .getJSONObject(0)
                                                    .getJSONObject("geometry")
                                                    .getJSONObject("location");

                                            locationSource = new LatLng(locationJSON.getDouble("lat"), locationJSON.getDouble("lng"));
                                            nameSource = place.description;
                                            route();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {


                                Log.d(TAG, "That didn't work!");
                                Log.e(TAG, error.toString());
                                error.printStackTrace();
                            }
                        });

                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);

                    }
                }
        );

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        spinnerTravelMode = (Spinner) view.findViewById(R.id.spinnerTravelMode);
        spinnerTravelMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinnerTravelModeFirstRun) {
                    spinnerTravelModeFirstRun = false;
                }
                else {
                    route();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void route() {
        googleMap.clear();

        googleMap.addMarker(new MarkerOptions().position(locationDestination).title(nameDestination));
        googleMap.addMarker(new MarkerOptions().position(locationSource).title(nameSource));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(locationSource).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        // Url to request directions from google maps api
        String url = getMapsApiDirectionsUrl(locationSource, locationDestination, TRAVEL_MODES[spinnerTravelMode.getSelectedItemPosition()]);
        Log.d(TAG, "URL to directions service: " + url);

        ReadTask downloadTask = new ReadTask();
        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }

    private String getMapsApiDirectionsUrl(LatLng origin, LatLng dest, String travel_mode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Mode of travel
        String mode = "mode=" + travel_mode;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    private class ReadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                MapHttpConnection http = new MapHttpConnection();
                data = http.readUr(url[0]);


            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }

    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }
                polyLineOptions.addAll(points);
                polyLineOptions.width(12);
                polyLineOptions.color(Color.BLUE);
            }
            googleMap.addPolyline(polyLineOptions);
        }
    }
}
