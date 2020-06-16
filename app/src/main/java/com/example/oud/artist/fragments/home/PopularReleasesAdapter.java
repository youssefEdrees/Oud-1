package com.example.oud.artist.fragments.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oud.R;

import java.util.ArrayList;
import java.util.Collections;

public class PopularReleasesAdapter extends  RecyclerView.Adapter<PopularReleasesAdapter.PopularReleasesItemViewHolder> implements ItemTouchHelperAdapter {

    ArrayList<String> tracksNames = new ArrayList<>();
    ArrayList<String> tracksIds = new ArrayList<>();
    Context context;

    private final OnStartDragListener mDragStartListener;


    public PopularReleasesAdapter(Context context,OnStartDragListener dragStartListener){
        this.mDragStartListener = dragStartListener;
        this.context = context;
   }

   public void addItem(String trackId,String trackName){
        tracksIds.add(trackId);
        tracksNames.add(trackName);
        notifyItemInserted(getItemCount()-1);
   }

    public void clearAdapter(){
        while(getItemCount()>0){
            tracksNames.remove(0);
            tracksIds.remove(0);
            notifyItemRemoved(0);
        }


    }

    public void removeItem(int position){
        tracksIds.remove(position);
        tracksNames.remove(position);
        notifyItemRemoved(position);
    }


    public ArrayList<String> getTracksIds(){
        return tracksIds;
    }


    @NonNull
    @Override
    public PopularReleasesItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_popular_releases_track, parent, false);
        return new PopularReleasesItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularReleasesItemViewHolder holder, int position) {
        holder.nameTextView.setText(tracksNames.get(position));


        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

    }



    @Override
    public int getItemCount() {
        return tracksIds.size();
    }

    @Override
    public void onItemDismiss(int position) {
        removeItem(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(tracksIds, fromPosition, toPosition);
        Collections.swap(tracksNames, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public class PopularReleasesItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        TextView nameTextView;
        ImageButton handleView;
         public PopularReleasesItemViewHolder(@NonNull View itemView) {
             super(itemView);
             nameTextView = itemView.findViewById(R.id.text_item_popular_releases_name);
             handleView = itemView.findViewById(R.id.btn_item_popular_releases_handle);

         }
        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
     }
}
