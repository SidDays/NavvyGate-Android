package com.sidrk.travelentsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private EditText editTextKeyword;
    private TextView textViewKeywordError;
    private Spinner spinnerCategory;
    private EditText editTextDistance;
    private EditText editTextOther;
    private RadioGroup radioGroupLocation;
    private RadioButton radioButtonCurrent;
    private RadioButton radioButtonOther;
    private TextView textViewOtherError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextKeyword = findViewById(R.id.editTextKeyword);
        textViewKeywordError = findViewById(R.id.textViewKeywordError);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        editTextDistance = findViewById(R.id.editTextDistance);
        editTextOther = findViewById(R.id.editTextOther);
        radioButtonOther = findViewById(R.id.radioButtonOther);
        radioButtonCurrent = findViewById(R.id.radioButtonCurrent);
        radioGroupLocation = findViewById(R.id.radioGroupLocation);
        radioGroupLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.radioButtonCurrent:
                        editTextOther.setEnabled(false);
                        break;
                    case R.id.radioButtonOther:
                        editTextOther.setEnabled(true);
                        break;
                    default:
                        Log.e(TAG, "Unknown radio button selected!");
                }
            }
        });
        textViewOtherError = findViewById(R.id.textViewOtherError);
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

            if(other.trim().length() == 0) {
                isValid = false;
                textViewOtherError.setVisibility(View.VISIBLE);
            } else {
                textViewOtherError.setVisibility(View.GONE);
            }
        } else {
            textViewOtherError.setVisibility(View.GONE);
        }

        if(!isValid) {
            Toast.makeText(getApplicationContext(),"Please fix all fields with errors",Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    public void nearbySearch(View v) {

        if (validate()) {

            Log.d(TAG, "Starting volley stuff...");

            // TODO: VOLLEY DEMO

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://ip-api.com/json";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            Log.d(TAG, "Response is: " + response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "That didn't work!");
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }
    }

    public void clearAll(View v) {

        editTextKeyword.setText("");
        editTextDistance.setText("");
        radioGroupLocation.check(R.id.radioButtonCurrent);
        editTextOther.setText("");
    }
}
