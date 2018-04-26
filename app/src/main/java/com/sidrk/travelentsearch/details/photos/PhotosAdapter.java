package com.sidrk.travelentsearch.details.photos;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sidrk.travelentsearch.R;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private PlacePhotoMetadataBuffer photoMetadataBuffer;

    // Maps stuff
    protected GeoDataClient mGeoDataClient;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public ImageView imageViewPhoto;

        public ViewHolder(ImageView v) {

            super(v);

            imageViewPhoto = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PhotosAdapter(PlacePhotoMetadataBuffer photoMetadataBufferParam, GeoDataClient myGeoDataClient) {
        photoMetadataBuffer = photoMetadataBufferParam;
        mGeoDataClient = myGeoDataClient;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PhotosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_photo, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        // TODO: Fetch this image
        PlacePhotoMetadata currentPhoto = photoMetadataBuffer.get(position);
        // Get the attribution text.
        // CharSequence attribution = currentPhoto.getAttributions();
        // Get a full-size bitmap for the photo.
        Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(currentPhoto);
        photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                PlacePhotoResponse photo = task.getResult();
                Bitmap bitmap = photo.getBitmap();

                holder.imageViewPhoto.setImageBitmap(bitmap);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return photoMetadataBuffer.getCount();
    }
}