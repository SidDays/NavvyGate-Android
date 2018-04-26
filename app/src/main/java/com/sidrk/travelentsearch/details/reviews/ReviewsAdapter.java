package com.sidrk.travelentsearch.details.reviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sidrk.travelentsearch.R;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private Review[] reviewsDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public LinearLayout linearLayoutReviews;
        public ImageView imageViewReviewerIcon;
        public TextView textViewReviewerName;
        public RatingBar ratingBarReviewRating;
        public TextView textViewReviewDate;
        public TextView textViewReviewText;

        public ViewHolder(LinearLayout v) {

            super(v);

            linearLayoutReviews = v;
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
        Review currentReview = reviewsDataset[position];

        // TODO: URL
        // TODO: Image
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
}