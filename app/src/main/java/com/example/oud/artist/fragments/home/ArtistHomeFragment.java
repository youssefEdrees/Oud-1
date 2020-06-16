package com.example.oud.artist.fragments.home;

import androidx.lifecycle.MutableLiveData;
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

import android.util.Log;
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
import com.example.oud.api.Track;
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

        ArrayList<TrackPreview>tracks = mViewModel.getAddedTracks();
        for(int i = 0 ; i < tracks.size() ; i++){
            adapter.addItem(tracks.get(i).get_id(),tracks.get(i).getName());
        }

        setAddedTrackObserver();
        setSaveOnClickListener();
        setAddSongOnClickListener();
        //recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupTopSongs();

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);



    }

    private void setupTopSongs(){
        mViewModel.getMyTopSongs(OudUtils.getToken(getContext()),OudUtils.getUserId(getContext())).observe(getViewLifecycleOwner(), new Observer<TopTracks>() {
            @Override
            public void onChanged(TopTracks topTracks) {
                TrackPreview [] tracks;
                if(!mViewModel.firstTopSongsFetch)
                    return;
                mViewModel.firstTopSongsFetch = false;
                tracks = topTracks.getTracks();

                for(int i=0;i<tracks.length;i++){
                    adapter.addItem(tracks[i].get_id(),tracks[i].getName());
                    mViewModel.addedTrack(tracks[i]);
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
                            adapter.clearAdapter();
                            mViewModel.firstTopSongsFetch = true;
                            mViewModel.myTopSongs = null;
                            mViewModel.addedTracks.clear();
                            setupTopSongs();
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

    private void setAddedTrackObserver(){
        MutableLiveData<TrackPreview> addedSongId = mViewModel.getAddedTrackForCallBack();
        addedSongId.observe(getViewLifecycleOwner(), new Observer<TrackPreview>() {
            @Override
            public void onChanged(TrackPreview trackPreview) {
                String id  = trackPreview.get_id();
                Boolean doesntExit = true;
                ArrayList<String> tracksIds=  adapter.getTracksIds();
                for(int i = 0 ;i<tracksIds.size();i++){
                    if(id.equals(tracksIds.get(i)))
                        doesntExit = false;
                }
                ArrayList<TrackPreview> addedTracks = mViewModel.getAddedTracks();
                for(int i = 0 ;i<addedTracks.size();i++){
                    if(id.equals(addedTracks.get(i).get_id()))
                        doesntExit = false;
                }
                if(doesntExit){
                adapter.addItem(trackPreview.get_id(),trackPreview.getName());
                mViewModel.addedTrack(trackPreview);
                }
                else
                    Toast.makeText(getContext(),"item already exists",Toast.LENGTH_LONG).show();
            }
        });


    }

    private void setAddSongOnClickListener(){
        addSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapter.getItemCount()>=10){
                    Toast.makeText(getContext(),"top songs can be 10 at most remove a song by swiping",Toast.LENGTH_LONG).show();
                    return;
                }

                MutableLiveData<TrackPreview> addedSongId = mViewModel.getAddedTrackForCallBack();

                mViewModel.addedTracks.clear();
                for(int i=0;i<adapter.getTracksIds().size();i++){
                    TrackPreview trackPreview = new TrackPreview(adapter.tracksIds.get(i),adapter.tracksNames.get(i),null,1,1,null);
                    mViewModel.addedTrack(trackPreview);
                }
                SongsListFragment.show(getActivity(),R.id.nav_host_fragment_artist,addedSongId);
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
