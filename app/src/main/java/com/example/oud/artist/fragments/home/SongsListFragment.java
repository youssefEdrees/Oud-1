package com.example.oud.artist.fragments.home;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oud.R;
import com.example.oud.connectionaware.ConnectionAwareFragment;


public class SongsListFragment extends ConnectionAwareFragment<ArtistHomeViewModel> {

    public SongsListFragment(Activity activity){
        super(ArtistHomeViewModel.class,
                R.layout.fragment_songs_list,
                activity.findViewById(R.id.progress_bar_artist_activity),
                activity.findViewById(R.id.block_view_artist_Activity),
                null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
