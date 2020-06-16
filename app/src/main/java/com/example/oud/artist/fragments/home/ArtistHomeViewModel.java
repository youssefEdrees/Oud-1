package com.example.oud.artist.fragments.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.oud.ConnectionStatusListener;
import com.example.oud.Constants;
import com.example.oud.api.Album;
import com.example.oud.api.OudList;
import com.example.oud.api.PopularTracksRequest;
import com.example.oud.api.TopTracks;
import com.example.oud.api.TrackPreview;
import com.example.oud.connectionaware.ConnectionAwareViewModel;

import java.util.ArrayList;


public class ArtistHomeViewModel extends ConnectionAwareViewModel<ArtistHomeRepository> {

    boolean firstTopSongsFetch = true;


    MutableLiveData<OudList<Album>> myAlbums;

    MutableLiveData<TopTracks> myTopSongs;

    ArrayList<TrackPreview> addedTracks= new ArrayList<>();
    MutableLiveData<TrackPreview> addedTrackForCallBack = new MutableLiveData<>();


    public MutableLiveData<TrackPreview> getAddedTrackForCallBack() {
        return addedTrackForCallBack;
    }

    public ArrayList<TrackPreview> getAddedTracks() {
        return addedTracks;
    }

    public void setAddedTracks(ArrayList<TrackPreview> addedTracks) {
        this.addedTracks = addedTracks;
    }

    public void addedTrack(TrackPreview addedTrack) {
        addedTracks.add(addedTrack);
    }

    public MutableLiveData<OudList<Album>> getMyAlbums(String token, String myId){
        if(myAlbums == null)
            myAlbums = mRepo.getMyAlbums(token,myId);

        return myAlbums;
    }



    public ArtistHomeViewModel(){
        super(new ArtistHomeRepository(), Constants.YAMANI_MOCK_BASE_URL);
    }



    public MutableLiveData<TopTracks> getMyTopSongs(String token, String myId){
        if(myTopSongs == null)
            myTopSongs = mRepo.getMyTopSongs(token,myId);

        return myTopSongs;
    }

    public MutableLiveData<Album> fetchAlbum(String token, String albumId){
        return mRepo.fetchAlbum(token, albumId);
    }

    public void savePopularSongs(String token, PopularTracksRequest popularTracksRequest, ConnectionStatusListener connectionStatusListener){
        mRepo.savePopularSongs(token, popularTracksRequest, connectionStatusListener);
    }

    @Override
    public void clearData() {

    }

}
