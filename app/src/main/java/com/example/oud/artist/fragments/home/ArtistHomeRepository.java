package com.example.oud.artist.fragments.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.oud.ConnectionStatusListener;
import com.example.oud.Constants;
import com.example.oud.api.Album;
import com.example.oud.api.OudList;
import com.example.oud.api.PopularTracksRequest;
import com.example.oud.api.TopTracks;
import com.example.oud.connectionaware.ConnectionAwareRepository;
import com.example.oud.connectionaware.FailureSuccessHandledCallback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ArtistHomeRepository extends ConnectionAwareRepository {

    public MutableLiveData<TopTracks> getMyTopSongs(String token, String myId){
        Call<TopTracks> call = oudApi.getTopTracks(token,myId);
        MutableLiveData<TopTracks> myTracks= new MutableLiveData<>();
        addCall(call).enqueue(new FailureSuccessHandledCallback<TopTracks>(this){
            @Override
            public void onResponse(Call<TopTracks> call, Response<TopTracks> response) {
                super.onResponse(call, response);
                if(response.isSuccessful()){
                    myTracks.setValue(response.body());
                }
            }
        });
        return myTracks;
    }

    public MutableLiveData<Album> fetchAlbum(String token, String albumId) {
        MutableLiveData<Album> albumMutableLiveData = new MutableLiveData<>();


        Call<Album> albumCall = oudApi.album(token, albumId);

        addCall(albumCall).enqueue(new FailureSuccessHandledCallback<Album>(this) {

            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                super.onResponse(call, response);
                if(response.isSuccessful())
                {
                    Album album = response.body();
                    albumMutableLiveData.setValue(album);
                }
            }
        });


        return albumMutableLiveData;
    }


    public MutableLiveData<OudList<Album>> getMyAlbums(String token,String myId){
        Call<OudList<Album>>call = oudApi.artistAlbums(token,myId,0, 50);
        MutableLiveData<OudList<Album>> myAlbums= new MutableLiveData<>();
        addCall(call).enqueue(new FailureSuccessHandledCallback<OudList<Album>>(this){
            @Override
            public void onResponse(Call<OudList<Album>> call, Response<OudList<Album>> response) {
                super.onResponse(call, response);
                if(response.isSuccessful()){
                    myAlbums.setValue(response.body());
                }
            }
        });
        return myAlbums;

    }


    public void savePopularSongs(String token, PopularTracksRequest popularTracksRequest, ConnectionStatusListener connectionStatusListener){
        Call<ResponseBody> call = oudApi.setTopTracks(token,popularTracksRequest);
        addCall(call).enqueue(new FailureSuccessHandledCallback<ResponseBody>(this,connectionStatusListener){});
    }



}
