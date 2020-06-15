package com.example.oud.artist.fragments.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.oud.ConnectionStatusListener;
import com.example.oud.Constants;
import com.example.oud.api.PopularTracksRequest;
import com.example.oud.api.TopTracks;
import com.example.oud.connectionaware.ConnectionAwareViewModel;
import com.example.oud.user.fragments.profile.ProfileRepository;

public class ArtistHomeViewModel extends ConnectionAwareViewModel<ArtistHomeRepository> {
    public ArtistHomeViewModel(){
        super(new ArtistHomeRepository(), Constants.YAMANI_MOCK_BASE_URL);
    }


    public MutableLiveData<TopTracks> getMyTopSongs(String token, String myId){
        return mRepo.getMyTopSongs(token,myId);
    }

    public void savePopularSongs(String token, PopularTracksRequest popularTracksRequest, ConnectionStatusListener connectionStatusListener){
        mRepo.savePopularSongs(token, popularTracksRequest, connectionStatusListener);
    }

    @Override
    public void clearData() {

    }

}
