package com.sidrk.travelentsearch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sidrk.travelentsearch.details.PlaceDetailsActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private static final String TAG = "ResultsAdapter";

    // default value for testing purposes - overwritten upon load
    private String resultJSON = "{\"html_attributions\":[],\"results\":[{\"geometry\":{\"location\":{\"lat\":34.0045174,\"lng\":-118.2568101},\"viewport\":{\"northeast\":{\"lat\":34.00586792989272,\"lng\":-118.2553063701073},\"southwest\":{\"lat\":34.00316827010727,\"lng\":-118.2580060298927}}},\"icon\":\"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\"id\":\"ac36331136da8c886a2767f7ecc673b3dc61ae95\",\"name\":\"Pizza Hut\",\"opening_hours\":{\"open_now\":false,\"weekday_text\":[]},\"photos\":[{\"height\":1000,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/113027627308564336177/photos\\\">A Google User</a>\"],\"photo_reference\":\"CmRaAAAAL1CeJT96joRVuTSvBMByd0cGWCHpaKiZeWHfvzZ1Ryz0oRKEDUac0K7mu5l3jnvVhcBEmfozAVki_Ep0zQpq3Ws_yGLsCkQWTzkM9UU7Cks1zGtqKGVbsk2eyimqMhxiEhCY0m-9wL3iNMxGookvp173GhR4DvIEd4lK0snHWVhmcfbXM2TQvQ\",\"width\":750}],\"place_id\":\"ChIJa1ek7WDIwoARvGx4gelpOeU\",\"price_level\":1,\"rating\":3.3,\"reference\":\"CmRbAAAAPGIpFw7KHCG9PPd5hoGzW_HVIXsDRqBQAtvMe4JDbdYFu_lDsz_obq_XKyZYeUG2UHNexThXDvZWPDbkS0H7b3GCC2HwjhiZQocCVWr6GfQyvZXezUBVI_2k-w99UbLuEhCzlNX97-9xGG79DBrruvyyGhQDOI-90FF2bZ2MsEM9F7t5Go1htg\",\"scope\":\"GOOGLE\",\"types\":[\"restaurant\",\"food\",\"point_of_interest\",\"establishment\"],\"vicinity\":\"4351 S Central Ave, Los Angeles\"},{\"geometry\":{\"location\":{\"lat\":34.0042149,\"lng\":-118.265452},\"viewport\":{\"northeast\":{\"lat\":34.00556472989272,\"lng\":-118.2641021701073},\"southwest\":{\"lat\":34.00286507010728,\"lng\":-118.2668018298927}}},\"icon\":\"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\"id\":\"6a11f59795dc48b0bef9520a5a8c8c0f276699b3\",\"name\":\"L A Express Pizza\",\"opening_hours\":{\"open_now\":false,\"weekday_text\":[]},\"photos\":[{\"height\":2988,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/101611381084353366803/photos\\\">ricardo salinas</a>\"],\"photo_reference\":\"CmRZAAAABtdQB1YLzJV5bQbkrkJlcYC4Txbw2P1EwVBxJV9WQISe_v6I0DkbuustjYQgRaIkO0SyTORI4svImw4IMe136GKPPiN87oYJMUDPRYRYpbAwHBkHhbOg7AE6x7Br6UR7EhBaGlYKUnEyw6mfrQRFfHfDGhR82B43P3Q3LXPZF5rAIGubXiz5GA\",\"width\":5312}],\"place_id\":\"ChIJhYuFjGjIwoARubm21DQO0_s\",\"price_level\":1,\"rating\":4.6,\"reference\":\"CmRbAAAArx5_W8-8WDP72YqE8QSHgyro3QN7qnzRlFvnoAChUkIwRbqmfA_oEoF1mlaXx96Ix9sPH_bTBwGI8DZVZtdrOciEBcZO5UGsAkj6EiO0opnqn45x_fvacn0pqYGOewN_EhBbdDmt5u5Td6s5vrQwUYWrGhTI2luEW7ObQCQAKtsN3202U5Hz8w\",\"scope\":\"GOOGLE\",\"types\":[\"restaurant\",\"food\",\"point_of_interest\",\"establishment\"],\"vicinity\":\"527 E Vernon Ave, Los Angeles\"},{\"geometry\":{\"location\":{\"lat\":34.0109655,\"lng\":-118.2697122},\"viewport\":{\"northeast\":{\"lat\":34.01240882989273,\"lng\":-118.2683626701073},\"southwest\":{\"lat\":34.00970917010729,\"lng\":-118.2710623298927}}},\"icon\":\"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\"id\":\"17fd5bb26544193465770193cba1cdec982a2a1b\",\"name\":\"La Pizza Loca #36\",\"opening_hours\":{\"open_now\":false,\"weekday_text\":[]},\"photos\":[{\"height\":720,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/102148599190513144632/photos\\\">Pizza Loca</a>\"],\"photo_reference\":\"CmRaAAAAmdnBGYzcPR2-qQgWNkGAdd7pw4XTsu_UVF5ab1g77n-eeHt5DWVYWynEWixMaSuvce3aZwuATaJWcQbi8fScyF6wvyHMG5TMsJc6dXKNpEZ8Q-ryFDsygbqWdwcCllnSEhCfqgwtybEVJxMX9iaimHQ6GhSvzEj_iXWBqCMYNsQPJ5w-ikY7YA\",\"width\":960}],\"place_id\":\"ChIJ38O_8XHIwoAR75Zojj5LzuM\",\"price_level\":1,\"rating\":3,\"reference\":\"CmRbAAAA0pPIxa_ytPxruVQjL_CWKq8DXJgljhxYhvdCjWYZhIk959OIGw2eba8Wm3oWZtSOPt050C6BH4WYq_oikVvP08Hpst63hWw6ut-2P2oTCm8mHfLnWlqYlB_7JZHsn3uaEhCalJDUd25erWNcGHnBw6EgGhTDfxHuA02sFetFYxTRHu7NBTB9sg\",\"scope\":\"GOOGLE\",\"types\":[\"meal_delivery\",\"meal_takeaway\",\"restaurant\",\"food\",\"point_of_interest\",\"establishment\"],\"vicinity\":\"250 E Martin Luther King Jr Blvd, Los Angeles\"}],\"status\":\"OK\"}";

    private ResultListItem[] dataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public TextView placeName;
        public TextView placeAddress;
        public ImageView placeIcon;
        public ImageView favoriteStatus;
        public LinearLayout linearLayoutPlaceClick;

        public ViewHolder(LinearLayout v) {
            super(v);
            placeName = v.findViewById(R.id.textViewPlaceName);
            placeAddress = v.findViewById(R.id.textViewPlaceAddress);
            placeIcon = v.findViewById(R.id.imageViewPlaceIcon);
            favoriteStatus = v.findViewById(R.id.imageViewFavoriteStatus);
            linearLayoutPlaceClick = v.findViewById(R.id.linearLayoutPlaceClick);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ResultsAdapter(ResultListItem[] myDataset) {

        try {
            if(myDataset == null) {
                dataset = ResultListItem.parseResponse(resultJSON);
            }
            else {
                dataset = myDataset;
            }

        } catch (JSONException e) {
            dataset = null;
            Log.e(TAG, "Default hardcoded JSON is invalid.");
            e.printStackTrace();
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_search_result, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        // Load contents of search results into this viewHolder
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final ResultListItem currentResult = dataset[position];

        holder.placeName.setText(currentResult.getName());
        holder.placeAddress.setText(currentResult.getAddress());
        String iconURL = currentResult.getIconURL();
        Picasso.get().load(iconURL).into(holder.placeIcon);
        holder.favoriteStatus.setImageResource(currentResult.getFavoriteStatusId());
        holder.favoriteStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.favoriteStatus.setImageResource(R.drawable.heart_fill_red);
            }
        });

        holder.linearLayoutPlaceClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO call place details api
                final Uri.Builder builder = Uri.parse(Constants.URL_PLACE_DETAILS).buildUpon();
                builder.appendQueryParameter("placeId", currentResult.getPlaceId());
                String url = builder.build().toString();
                Log.d(TAG, "Generated URL for place details: " + url);

                callRequest(url, v);

            }
        });
    }

    private ProgressDialog dialog;
    private void callRequest(String url, final View v) {

        dialog = new ProgressDialog(v.getContext());
        dialog.setMessage("Fetching results");
        dialog.show();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(v.getContext());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responseString) {

                        if(dialog.isShowing()) { dialog.dismiss(); }

                        // Display the first 500 characters of the response string.
                        Log.v(TAG, "Response is: " + responseString);

                        // Start a new activity with the results
                        Intent myIntent = new Intent(v.getContext(), PlaceDetailsActivity.class);
                        myIntent.putExtra("resultJSON", responseString);
                        v.getContext().startActivity(myIntent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(dialog.isShowing()) { dialog.dismiss(); }

                Log.d(TAG, "That didn't work!");
                Log.e(TAG, error.toString());
                error.printStackTrace();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.length;
    }
}