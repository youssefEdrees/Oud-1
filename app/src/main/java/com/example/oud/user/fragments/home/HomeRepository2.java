package com.example.oud.user.fragments.home;

import android.util.Log;

import com.example.oud.Constants;
import com.example.oud.api.Album;
import com.example.oud.api.Artist;
import com.example.oud.api.Category2;
import com.example.oud.api.OudApi;
import com.example.oud.api.OudList;
import com.example.oud.api.Playlist;
import com.example.oud.api.RecentlyPlayedTracks2;
import com.example.oud.connectionaware.ConnectionAwareRepository;
import com.example.oud.connectionaware.FailureSuccessHandledCallback;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

public class HomeRepository2 extends ConnectionAwareRepository {

    private static final String TAG = HomeRepository2.class.getSimpleName();

    public static final HomeRepository2 ourInstance = new HomeRepository2();

    public static HomeRepository2 getInstance() {
        return ourInstance;
    }

    private HomeRepository2() {

    }

    public MutableLiveData<RecentlyPlayedTracks2> fetchRecentlyPlayedTracks() {
        MutableLiveData<RecentlyPlayedTracks2> recentlyPlayedLiveData = new MutableLiveData<>();

        OudApi oudApi = instantiateRetrofitOudApi();
        Call<RecentlyPlayedTracks2> recentlyPlayedCall =
                oudApi.recentlyPlayedTracks2(Constants.USER_HOME_HORIZONTAL_RECYCLERVIEW_ITEM_COUNT, null, null);

        addCall(recentlyPlayedCall).enqueue(new FailureSuccessHandledCallback<RecentlyPlayedTracks2>(this) {
            @Override
            public void onResponse(Call<RecentlyPlayedTracks2> call, Response<RecentlyPlayedTracks2> response) {
                super.onResponse(call, response);

                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.code());
                    return;
                }

                if (response.code() == 204) {
                    recentlyPlayedLiveData.setValue(null);
                    return;
                }


                recentlyPlayedLiveData.setValue(response.body());
            }
        });

        return recentlyPlayedLiveData;
    }

    public MutableLiveData<Album> fetchAlbum(String albumId) {
        MutableLiveData<Album> albumMutableLiveData = new MutableLiveData<>();

        OudApi oudApi = instantiateRetrofitOudApi();
        Call<Album> albumCall = oudApi.album(albumId);

        addCall(albumCall).enqueue(new FailureSuccessHandledCallback<Album>(this) {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                super.onResponse(call, response);

                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.code());
                    return;
                }

                albumMutableLiveData.setValue(response.body());
            }
        });

        return albumMutableLiveData;
    }

    public MutableLiveData<Artist> fetchArtist(String artistId) {
        MutableLiveData<Artist> artistMutableLiveData = new MutableLiveData<>();

        OudApi oudApi = instantiateRetrofitOudApi();
        Call<Artist> artistCall = oudApi.artist(artistId);

        addCall(artistCall).enqueue(new FailureSuccessHandledCallback<Artist>(this) {
            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {
                super.onResponse(call, response);

                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.code());
                    return;
                }

                artistMutableLiveData.setValue(response.body());
            }
        });

        return artistMutableLiveData;
    }

    public MutableLiveData<Playlist> fetchPlaylist(String playlistId) {
        MutableLiveData<Playlist> playlistMutableLiveData = new MutableLiveData<>();

        OudApi oudApi = instantiateRetrofitOudApi();
        Call<Playlist> playlistCall = oudApi.playlist(playlistId);

        addCall(playlistCall).enqueue(new FailureSuccessHandledCallback<Playlist>(this) {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                super.onResponse(call, response);

                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.code());
                    return;
                }

                playlistMutableLiveData.setValue(response.body());
            }
        });

        return playlistMutableLiveData;
    }

    public MutableLiveData<OudList<Category2>> fetchCategoryList() {
        MutableLiveData<OudList<Category2>> categoryListLiveData = new MutableLiveData<>();

        OudApi oudApi = instantiateRetrofitOudApi();
        Call<OudList<Category2>> categoryListCall = oudApi.listOfCategories2(null, Constants.USER_HOME_CATEGORIES_COUNT);

        addCall(categoryListCall).enqueue(new FailureSuccessHandledCallback<OudList<Category2>>(this) {
            @Override
            public void onResponse(Call<OudList<Category2>> call, Response<OudList<Category2>> response) {
                super.onResponse(call, response);

                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.code());
                    return;
                }

                categoryListLiveData.setValue(response.body());
            }
        });

        return categoryListLiveData;
    }

    public MutableLiveData<OudList<Playlist>> fetchCategoryPlaylists(String categoryId) {
        MutableLiveData<OudList<Playlist>> categoryPlaylistsLiveData = new MutableLiveData<>();

        OudApi oudApi = instantiateRetrofitOudApi();
        Call<OudList<Playlist>> playlistsCall = oudApi.categoryPlaylist(categoryId, null, Constants.USER_HOME_HORIZONTAL_RECYCLERVIEW_ITEM_COUNT);

        addCall(playlistsCall).enqueue(new FailureSuccessHandledCallback<OudList<Playlist>>(this) {
            @Override
            public void onResponse(Call<OudList<Playlist>> call, Response<OudList<Playlist>> response) {
                super.onResponse(call, response);

                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.code());
                    return;
                }

                categoryPlaylistsLiveData.setValue(response.body());
            }
        });

        return categoryPlaylistsLiveData;
    }


}