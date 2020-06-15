package com.example.oud.artist.fragments.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.oud.Constants;
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
    @Override
    public void clearData() {

    }

}
