package com.sidrk.travelentsearch.details.reviews;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sidrk.travelentsearch.R;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private Review[] reviewsDataset;

    private static final Comparator<Review> ORDER_DEFAULT = new Comparator<Review>() {
        @Override
        public int compare(Review o1, Review o2) {
            return Integer.compare(o1.naturalOrder, o2.naturalOrder);
        }
    };
    private static final Comparator<Review> ORDER_RATING = new Comparator<Review>() {
        @Override
        public int compare(Review o1, Review o2) {
            return Float.compare(o1.rating, o2.rating);
        }
    };
    private static final Comparator<Review> ORDER_RECENT = new Comparator<Review>() {
        @Override
        public int compare(Review o1, Review o2) {
            return Long.compare(o1.timeStamp, o2.timeStamp);
        }
    };

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public LinearLayout linearLayoutReview;
        public ImageView imageViewReviewerIcon;
        public TextView textViewReviewerName;
        public RatingBar ratingBarReviewRating;
        public TextView textViewReviewDate;
        public TextView textViewReviewText;

        public ViewHolder(LinearLayout v) {

            super(v);

            linearLayoutReview = v;
            imageViewReviewerIcon = v.findViewById(R.id.imageViewReviewerIcon);
            textViewReviewerName = v.findViewById(R.id.textViewReviewerName);
            ratingBarReviewRating = v.findViewById(R.id.ratingBarReviewRating);
            textViewReviewDate = v.findViewById(R.id.textViewReviewDate);
            textViewReviewText = v.findViewById(R.id.textViewReviewText);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ReviewsAdapter(Review[] myDataset) {
        reviewsDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_review, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // replace the contents of the view with that element
        final Review currentReview = reviewsDataset[position];

        holder.linearLayoutReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(currentReview.reviewURL));
                v.getContext().startActivity(intent);
            }
        });
        Picasso.get().load(currentReview.authorProfilePicURL).into(holder.imageViewReviewerIcon);
        holder.textViewReviewerName.setText(currentReview.authorName);
        holder.ratingBarReviewRating.setNumStars(5);
        holder.ratingBarReviewRating.setRating(currentReview.rating);
        holder.textViewReviewDate.setText(currentReview.date);
        holder.textViewReviewText.setText(currentReview.reviewText);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return reviewsDataset.length;
    }

    public void sortOption(int option) {
        switch (option) {
            case 2:
                Arrays.sort(reviewsDataset, ORDER_RATING);
                break;
            case 1:
                Arrays.sort(reviewsDataset, Collections.<Review>reverseOrder(ORDER_RATING));
                break;
            case 4:
                Arrays.sort(reviewsDataset, ORDER_RECENT);
                break;
            case 3:
                Arrays.sort(reviewsDataset, Collections.<Review>reverseOrder(ORDER_RECENT));
                break;
            default:
                Arrays.sort(reviewsDataset, ORDER_DEFAULT);
        }
        notifyDataSetChanged();
    }
}