package com.example.oud.artist.fragments.home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.oud.ConnectionStatusListener;
import com.example.oud.OudUtils;
import com.example.oud.R;
import com.example.oud.api.PopularTracksRequest;
import com.example.oud.api.TopTracks;
import com.example.oud.api.TrackPreview;
import com.example.oud.connectionaware.ConnectionAwareFragment;
import com.example.oud.user.fragments.profile.ProfileFragment;
import com.example.oud.user.fragments.profile.ProfileViewModel;

import java.util.ArrayList;

public class ArtistHomeFragment extends ConnectionAwareFragment<ArtistHomeViewModel> implements OnStartDragListener{

    private ItemTouchHelper mItemTouchHelper;
    PopularReleasesAdapter adapter;
    Button saveButton;
    Button addSongButton;

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveButton = view.findViewById(R.id.btn_save_popular_songs);
        addSongButton = view.findViewById(R.id.btn_add_popular_song);

        adapter = new PopularReleasesAdapter(getContext(),this);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_popular_release);

        setSaveOnClickListener();

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        mViewModel.getMyTopSongs(OudUtils.getToken(getContext()),OudUtils.getUserId(getContext())).observe(getViewLifecycleOwner(), new Observer<TopTracks>() {
            @Override
            public void onChanged(TopTracks topTracks) {
                TrackPreview [] tracks;
                tracks = topTracks.getTracks();
                for(int i=0;i<tracks.length;i++){
                    adapter.addItem(tracks[i].get_id(),tracks[i].getName());
                }
            }
        });
    }


    private void setSaveOnClickListener(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     ArrayList<String> tracksIds = adapter.getTracksIds();
                     String [] tracks = new String[tracksIds.size()];
                     tracks = tracksIds.toArray(tracks);
                     PopularTracksRequest popularTracksRequest= new PopularTracksRequest(tracks);
                    ConnectionStatusListener connectionStatusListener = new ConnectionStatusListener() {
                        @Override
                        public void onConnectionSuccess() {
                            Toast.makeText(getContext(),"Top songs updated",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onConnectionFailure() {
                            Toast.makeText(getContext(),"Top songs update failed",Toast.LENGTH_LONG).show();
                        }
                    };
                     mViewModel.savePopularSongs(OudUtils.getToken(getContext()),popularTracksRequest,connectionStatusListener);
            }
        });
    }

    public ArtistHomeFragment(Activity activity){
        super(ArtistHomeViewModel.class,
                R.layout.fragment_home_artist,
                activity.findViewById(R.id.progress_bar_artist_activity),
                activity.findViewById(R.id.block_view_artist_Activity),
                null);
    }

    public static ArtistHomeFragment newInstance(String userId, Activity activity) {
        ArtistHomeFragment artistHomeFragment = new ArtistHomeFragment(activity);


        return artistHomeFragment;
    }


}
