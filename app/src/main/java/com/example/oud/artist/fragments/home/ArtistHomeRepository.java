package com.example.oud.artist.fragments.home;

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

    public void savePopularSongs(String token, PopularTracksRequest popularTracksRequest, ConnectionStatusListener connectionStatusListener){
        Call<ResponseBody> call = oudApi.setTopTracks(token,popularTracksRequest);
        addCall(call).enqueue(new FailureSuccessHandledCallback<ResponseBody>(this,connectionStatusListener){});
    }


}
