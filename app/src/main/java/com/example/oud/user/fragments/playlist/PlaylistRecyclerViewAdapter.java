package com.example.oud.user.fragments.playlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.oud.R;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlaylistRecyclerViewAdapter extends RecyclerView.Adapter<PlaylistRecyclerViewAdapter.TrackItemViewHolder> {

    private Context mContext;

    private ArrayList<String> mTrackImages;
    private ArrayList<String> mTrackNames;

    public PlaylistRecyclerViewAdapter(Context mContext, ArrayList<String> mTrackImages, ArrayList<String> mTrackNames) {
        this.mContext = mContext;
        this.mTrackImages = mTrackImages;
        this.mTrackNames = mTrackNames;
    }

    @NonNull
    @Override
    public TrackItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_playlist_track, parent, false);
        return new TrackItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackItemViewHolder holder, int position) {

        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();

        Glide.with(mContext)
                .load(mTrackImages.get(position))
                .placeholder(R.drawable.ic_loading)
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .into(holder.mTrackImage);


        holder.mTrackName.setText(mTrackNames.get(position));

    }

    @Override
    public int getItemCount() {
        return mTrackNames.size();
    }

    public ArrayList<String> getTrackImages() {
        return mTrackImages;
    }

    public ArrayList<String> getTrackNames() {
        return mTrackNames;
    }

    static class TrackItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mTrackImage;
        private TextView mTrackName;

        TrackItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mTrackImage = itemView.findViewById(R.id.img_track_playlist);
            mTrackName = itemView.findViewById(R.id.txt_track_playlist);
            mTrackName.setSelected(true);
        }
    }
}
