package com.example.oud.user.fragments.profile;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.oud.ConnectionStatusListener;
import com.example.oud.Constants;
import com.example.oud.connectionaware.FailureSuccessHandledCallback;
import com.example.oud.api.LoggedInUser;
import com.example.oud.api.OudApi;
import com.example.oud.api.PlaylistPreview;
import com.example.oud.api.ProfilePreview;
import com.example.oud.api.UserOrArtistPreview;
import com.example.oud.api.UserPlaylistsResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileRepository  {
    private ConnectionStatusListener listener;
    private OudApi oudApi;



    public ProfileRepository(ConnectionStatusListener listener) {
        this.listener = listener;
        String base = Constants.BASE_URL;
        if(Constants.MOCK)
            base = Constants.YAMANI_MOCK_BASE_URL;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        oudApi = retrofit.create(OudApi.class);
    }

    public MutableLiveData<ProfilePreview> loadProfile(String userId,String token){
        MutableLiveData<ProfilePreview> mutableProfile = new MutableLiveData<>();

        Log.e("ProfileRepository",token);
        Call<ProfilePreview> call = oudApi.getUserById("Bearer "+token,userId);

        call.enqueue(new FailureSuccessHandledCallback<ProfilePreview>(listener){
            @Override
            public void onResponse(Call call, Response response) {
                super.onResponse(call, response);
                if (response.isSuccessful())
                    mutableProfile.setValue((ProfilePreview) response.body());
                else
                    Log.e("tag",response.toString());
            }


        });

        return mutableProfile;
    }





    public void setProfileImage(String token , Uri newImage,Bitmap bitmap,Context context){

        Log.e("profile Repository","image  repo started");


        File f = new File(context.getCacheDir(), "image.png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

//Convert bitmap to byte array

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapdata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        RequestBody requestFile = RequestBody.create(f,MediaType.parse("multipart/form-data"));

        MultipartBody.Part body = MultipartBody.Part.createFormData("image", f.getName(), requestFile);

        Call<LoggedInUser> call = oudApi.updateUserPicture("Bearer "+token,body);
        call.enqueue(new FailureSuccessHandledCallback<LoggedInUser>(listener) {
            @Override
            public void onResponse(Call<LoggedInUser> call, Response<LoggedInUser> response) {
                if(response.isSuccessful())
                    Log.e("profile Repository","image uploaded");
                else
                    Log.e("profile Repository",response.toString());

            }

            @Override
            public void onFailure(Call<LoggedInUser> call, Throwable t) {
                Log.e("profile Repository","image not uploaded");
                Log.e("profile Repository",t.getMessage());



            }
        });



    }







}
