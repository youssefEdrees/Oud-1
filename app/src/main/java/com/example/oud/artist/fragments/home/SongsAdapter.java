package com.example.oud.artist.fragments.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oud.GenericVerticalRecyclerViewAdapter;
import com.example.oud.R;

import java.util.ArrayList;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> {

        private ArrayList<String> songsNames = new ArrayList<>();
        private ArrayList<String> songsIds = new ArrayList<>();
        private GenericVerticalRecyclerViewAdapter.OnItemClickListener mItemClickListener;

        public SongsAdapter(GenericVerticalRecyclerViewAdapter.OnItemClickListener itemClickListener){
            mItemClickListener = itemClickListener;
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            GenericVerticalRecyclerViewAdapter.OnItemClickListener mItemClickListener;

            public TextView songNameTextView;
            private ConstraintLayout mLayout;

            public MyViewHolder(View v1, GenericVerticalRecyclerViewAdapter.OnItemClickListener itemClickListener) {
                super(v1);
                mItemClickListener = itemClickListener;
                songNameTextView   = v1.findViewById(R.id.txt_song_name);
                mLayout = itemView.findViewById(R.id.item_song_parent);

                mLayout.setOnClickListener(v -> SongsAdapter.MyViewHolder.this.mItemClickListener.onItemClickListener(getAdapterPosition(), v));
            }
        }



        public String getId(int position){
            return songsIds.get(position);
        }

        public String getName(int position){
            return songsNames.get(position);
        }


        @Override
        public SongsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);


            return new SongsAdapter.MyViewHolder(v,mItemClickListener);
        }


        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.songNameTextView.setText(songsNames.get(position));
        }


        @Override
        public int getItemCount() {
            return songsIds.size();
        }

        public void addItem(String name,String id){
            songsIds.add(id);
            songsNames.add(name);
            notifyItemInserted(getItemCount()-1);
        }
}


