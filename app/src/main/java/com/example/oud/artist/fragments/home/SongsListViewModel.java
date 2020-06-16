package com.example.oud.artist.fragments.home;

import androidx.lifecycle.MutableLiveData;

import com.example.oud.Constants;
import com.example.oud.api.Album;
import com.example.oud.api.OudList;
import com.example.oud.api.TopTracks;
import com.example.oud.connectionaware.ConnectionAwareViewModel;

public class SongsListViewModel extends ConnectionAwareViewModel<ArtistHomeRepository> {
    MutableLiveData<OudList<Album>> myAlbums;

    public SongsListViewModel(){
        super(new ArtistHomeRepository(), Constants.YAMANI_MOCK_BASE_URL);
    }

    public MutableLiveData<OudList<Album>> getMyAlbums(String token, String myId){
        if(myAlbums == null)
            myAlbums = mRepo.getMyAlbums(token,myId);

        return myAlbums;
    }

    public MutableLiveData<Album> fetchAlbum(String token, String albumId){
        return mRepo.fetchAlbum(token, albumId);
    }


    @Override
    public void clearData() {

    }
}
