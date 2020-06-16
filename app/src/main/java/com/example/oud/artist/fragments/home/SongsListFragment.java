package com.example.oud.artist.fragments.home;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.oud.GenericVerticalRecyclerViewAdapter;
import com.example.oud.OudUtils;
import com.example.oud.R;
import com.example.oud.api.Album;
import com.example.oud.api.OudList;
import com.example.oud.api.TopTracks;
import com.example.oud.api.TrackPreview;
import com.example.oud.connectionaware.ConnectionAwareFragment;
import com.example.oud.user.fragments.settings.UpdatePasswordFragment;

import java.util.ArrayList;


public class SongsListFragment extends ConnectionAwareFragment<SongsListViewModel> {
    RecyclerView recyclerView;

    SongsAdapter adapter;
    MutableLiveData<TrackPreview> addedSongId;




    public static void show(FragmentActivity activity,
                            @IdRes int containerId,
                            MutableLiveData<TrackPreview> addedSongId1) {

        FragmentManager manager = activity.getSupportFragmentManager();

        SongsListFragment songsListFragment = new SongsListFragment(activity,addedSongId1);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(containerId, songsListFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }


    public SongsListFragment(Activity activity,MutableLiveData<TrackPreview> addedSongId1){
        super(SongsListViewModel.class,
                R.layout.fragment_songs_list,
                activity.findViewById(R.id.progress_bar_artist_activity),
                activity.findViewById(R.id.block_view_artist_Activity),
                null);
        addedSongId =addedSongId1;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view_songs_selection);
        GenericVerticalRecyclerViewAdapter.OnItemClickListener  onItemClickListener= new GenericVerticalRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                TrackPreview trackPreview = new TrackPreview(adapter.getId(position),adapter.getName(position),null,4,4,null);
                addedSongId.setValue(trackPreview);
                Log.e("SongList","valueSet");
                getParentFragmentManager().popBackStack();
            }
        };
        adapter = new SongsAdapter(onItemClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    mViewModel.getMyAlbums(OudUtils.getToken(getContext()),OudUtils.getUserId(getContext())).observe(getViewLifecycleOwner(), new Observer<OudList<Album>>() {
        @Override
        public void onChanged(OudList<Album> albumOudList) {
            ArrayList<Album> albums = albumOudList.getItems();
            Toast.makeText(getContext(),albums.size()+"",Toast.LENGTH_LONG).show();
            String token = OudUtils.getToken(getContext());
            for(int i =0 ; i < albums.size();i++){
                mViewModel.fetchAlbum(token,albums.get(i).get_id()).observe(getViewLifecycleOwner(), new Observer<Album>() {
                    @Override
                    public void onChanged(Album album) {
                        ArrayList<TrackPreview> songs = album.getTracks().getItems();
                        for(int i =0 ;i<songs.size();i++){
                            adapter.addItem(songs.get(i).getName(),songs.get(i).get_id());
                        }
                    }
                });
            }
        }
    });

    }

}
