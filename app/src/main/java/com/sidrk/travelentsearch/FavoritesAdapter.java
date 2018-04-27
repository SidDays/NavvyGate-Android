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

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private static final String TAG = "FavoritesAdapter";

    private List<ResultListItem> favorites;
//
//    public int getCount() {
//        return favorites.s
//    }

    public boolean addFavorite(ResultListItem r) {

        for(int i = 0; i < favorites.size(); i++) {
            ResultListItem current = favorites.get(i);

            if(current.getPlaceId() == r.getPlaceId())
            {
                removeFavorite(r);
                return false;
            }
        }

        favorites.add(r);
        notifyDataSetChanged();
        return true;
    }
    public boolean removeFavorite(ResultListItem r) {

        for(int i = 0; i < favorites.size(); i++) {
            ResultListItem current = favorites.get(i);

            if(current.getPlaceId() == r.getPlaceId())
            {
                favorites.remove(i);
                notifyDataSetChanged();
                return true;
            }
        }

        return false;
    }

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

    // Provide a suitable constructor (depends on the kind of favorites)
    public FavoritesAdapter() {

        favorites = new ArrayList<>();

    }

    // Create new views (invoked by the layout manager)
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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
        // - get element from your favorites at this position
        // - replace the contents of the view with that element
        final ResultListItem currentResult = favorites.get(position);

        holder.placeName.setText(currentResult.getName());
        holder.placeAddress.setText(currentResult.getAddress());
        String iconURL = currentResult.getIconURL();
        Picasso.get().load(iconURL).into(holder.placeIcon);
        holder.favoriteStatus.setImageResource(currentResult.getFavoriteStatusId());
        holder.favoriteStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(FavoritesFragment.mAdapter.addFavorite(currentResult)) {
                    holder.favoriteStatus.setImageResource(R.drawable.heart_fill_red);
                } else {
                    holder.favoriteStatus.setImageResource(R.drawable.heart_outline_black);
                }
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

    // Return the size of your favorites (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return favorites.size();
    }
}