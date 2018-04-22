package com.sidrk.travelentsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sidrk.travelentsearch.adapters.ResultsAdapter;

public class ResultsActivity extends AppCompatActivity {

    private static final String TAG = "ResultsActivity";

    private RecyclerView recyclerViewResults;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        setContentView(R.layout.activity_results);
        recyclerViewResults = (RecyclerView) findViewById(R.id.recyclerViewResults);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewResults.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerViewResults.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ResultsAdapter(null);
        recyclerViewResults.setAdapter(mAdapter);


    }
}
