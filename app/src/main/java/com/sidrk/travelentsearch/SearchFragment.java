package com.sidrk.travelentsearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";

    private static final List<String> CATEGORIES = Arrays.asList("Default", "Airport", "Amusement Park", "Aquarium", "Art Gallery", "Bakery", "Bar", "Beauty Salon", "Bowling Alley", "Bus Station", "Cafe", "Campground", "Car Rental", "Casino", "Lodging", "Movie Theater", "Museum", "Night Club", "Park", "Parking", "Restaurant", "Shopping Mall", "Stadium", "Subway Station", "Taxi Stand", "Train Station", "Transit Station", "Travel Agency", "Zoo");

    private EditText editTextKeyword;
    private TextView textViewKeywordError;
    private Spinner spinnerCategory;
    private EditText editTextDistance;
    private EditText editTextOther;
    private RadioGroup radioGroupLocation;
    private RadioButton radioButtonCurrent;
    private RadioButton radioButtonOther;
    private TextView textViewOtherError;
    private Button buttonSearch;
    private Button buttonClear;

    public SearchFragment() {

    }

    // TODO: Add newInstance method?

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextKeyword = view.findViewById(R.id.editTextKeyword);
        textViewKeywordError = view.findViewById(R.id.textViewKeywordError);

        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, CATEGORIES);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) view.findViewById(R.id.spinnerCategory);
        sItems.setAdapter(spinnerAdapter);

        editTextDistance = view.findViewById(R.id.editTextDistance);
        editTextOther = view.findViewById(R.id.editTextOther);
        radioButtonOther = view.findViewById(R.id.radioButtonOther);
        radioButtonCurrent = view.findViewById(R.id.radioButtonCurrent);
        radioGroupLocation = view.findViewById(R.id.radioGroupLocation);
        radioGroupLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.radioButtonCurrent:
                        editTextOther.setEnabled(false);
                        textViewOtherError.setVisibility(View.GONE);
                        break;
                    case R.id.radioButtonOther:
                        editTextOther.setEnabled(true);
                        break;
                    default:
                        Log.e(TAG, "Unknown radio button selected!");
                }
            }
        });
        textViewOtherError = view.findViewById(R.id.textViewOtherError);

        buttonSearch = view.findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                nearbySearch(v);
            }
        });

        buttonClear = view.findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                clearAll(v);
            }
        });


    }

    /**
     * Checks if the keyword field (and the other location, if selected) fields are nonempty, and
     * returns true if they are. Makes any relevant error messages visible.
     *
     * @return
     */
    private boolean validate() {

        boolean isValid = true;
        String keyword = editTextKeyword.getText().toString();
        if (keyword.trim().length() == 0) {
            isValid = false;
            textViewKeywordError.setVisibility(View.VISIBLE);
        } else {
            textViewKeywordError.setVisibility(View.GONE);
        }

        String other = editTextOther.getText().toString();
        if (radioButtonOther.isChecked()) {

            if (other.trim().length() == 0) {
                isValid = false;
                textViewOtherError.setVisibility(View.VISIBLE);
            } else {
                textViewOtherError.setVisibility(View.GONE);
            }
        } else {
            textViewOtherError.setVisibility(View.GONE);
        }

        if (!isValid) {
            Toast.makeText(getActivity().getApplicationContext(), "Please fix all fields with errors", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    /**
     * Search button
     * @param v
     */
    public void nearbySearch(View v) {

        if (validate()) {

            Log.d(TAG, "Starting volley stuff...");

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

            // Build the request
            String url = buildRequest();

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String responseString) {
                            // Display the first 500 characters of the response string.
                            Log.v(TAG, "Response is: " + responseString);

                            // Start a new activity with the results
                            Intent myIntent = new Intent(getActivity(), ResultsActivity.class);
                            myIntent.putExtra("resultJSON", responseString);
                            startActivity(myIntent);

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

    /**
     * Creates a request URL to the backend
     * @return
     */
    private String buildRequest() {

        Uri.Builder builder = Uri.parse(Constants.URL_NEARBY_SEARCH).buildUpon();

        String keyword = editTextKeyword.getText().toString().trim();
        builder.appendQueryParameter("keyword", keyword);

        String category = spinnerCategory.getSelectedItem().toString().replace(" ", "_").toLowerCase();
        builder.appendQueryParameter("category", category);

        String distanceString = editTextDistance.getText().toString();
        if(distanceString.trim().length() > 0) {
            try {
                float radius = Float.parseFloat(distanceString) * 1609.344f;
                builder.appendQueryParameter("distance", String.valueOf(radius));
            } catch (NumberFormatException e) {
                Log.e(TAG, "Distance must be numeric.");
                Toast.makeText(getActivity().getApplicationContext(), "Distance is non-numeric, ignoring", Toast.LENGTH_SHORT).show();
            }
        }

        // TODO: Location
        String other = editTextOther.getText().toString();

        String url = builder.build().toString();
        Log.d(TAG, "Generated URL: "+url);
        return url;
    }

    /**
     * Clears text fields and resets form controls.
     * @param v
     */
    public void clearAll(View v) {

        editTextKeyword.setText("");
        editTextDistance.setText("");
        radioGroupLocation.check(R.id.radioButtonCurrent);
        editTextOther.setText("");
        textViewOtherError.setVisibility(View.GONE);
        textViewKeywordError.setVisibility(View.GONE);
        spinnerCategory.setSelection(0);
    }
}