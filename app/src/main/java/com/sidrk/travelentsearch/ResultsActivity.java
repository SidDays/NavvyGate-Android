package com.sidrk.travelentsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONException;

public class ResultsActivity extends AppCompatActivity {

    private static final String TAG = "ResultsActivity";

    private RecyclerView recyclerViewResults;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_results);
        recyclerViewResults = (RecyclerView) findViewById(R.id.recyclerViewResults);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewResults.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerViewResults.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String responseString = null;
        if (bundle != null) {
            responseString = bundle.getString("resultJSON");

            ResultListItem[] array = null;
            try {
                array = ResultListItem.parseResponse(responseString);

                // specify an adapter
                mAdapter = new ResultsAdapter(array);

            } catch (JSONException e) {

                // specify an adapter
                mAdapter = new ResultsAdapter(null);
                Log.e(TAG, "Unable to parse result response JSON string.");

                e.printStackTrace();
            }

            recyclerViewResults.setAdapter(mAdapter);
        } else {
            Log.d(TAG, "No response string passed. Using default data");
            mAdapter = new ResultsAdapter(null);
            recyclerViewResults.setAdapter(mAdapter);
        }
    }
}
