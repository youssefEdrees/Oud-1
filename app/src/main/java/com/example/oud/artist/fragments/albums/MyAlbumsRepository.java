package com.example.oud.artist.fragments.albums;

import androidx.lifecycle.MutableLiveData;

import com.example.oud.ConnectionStatusListener;
import com.example.oud.Constants;
import com.example.oud.OudUtils;
import com.example.oud.api.Album;
import com.example.oud.api.OudList;
import com.example.oud.connectionaware.ConnectionAwareRepository;
import com.example.oud.connectionaware.FailureSuccessHandledCallback;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class MyAlbumsRepository extends ConnectionAwareRepository {


    public MutableLiveData<OudList<Album>> getMyAlbums(String token,String myId){
        Call<OudList<Album>>call = oudApi.artistAlbums(token,myId,0, Constants.USER_ARTIST_ALBUMS_SINGLE_FETCH_LIMIT);
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


    public void getMoreAlbums(String token, String myId, int offset, MutableLiveData<OudList<Album>> albumList){
        Call<OudList<Album>>call = oudApi.artistAlbums(token,myId,offset, Constants.USER_ARTIST_ALBUMS_SINGLE_FETCH_LIMIT);

        addCall(call).enqueue(new FailureSuccessHandledCallback<OudList<Album>>(this){
            @Override
            public void onResponse(Call<OudList<Album>> call, Response<OudList<Album>> response) {
                super.onResponse(call, response);
                if(response.isSuccessful()){
                    OudList<Album> albums =  albumList.getValue();
                    albums.setOffset(response.body().getOffset());
                    albums.addItems(response.body().getItems());
                    albums.setTotal(response.body().getTotal());
                    albumList.setValue(albums);
                }
            }
        });
    }


    public void deleteAnAlbum(String token, String albumId, ConnectionStatusListener connectionStatusListener){
        Call<ResponseBody>call = oudApi.deleteAlbum(token,albumId);
        addCall(call).enqueue(new FailureSuccessHandledCallback<ResponseBody>(this,connectionStatusListener){});


    }
}
