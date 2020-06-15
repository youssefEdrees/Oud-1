package com.example.oud.user.fragments.settings;

import androidx.lifecycle.MutableLiveData;

import com.example.oud.ConnectionStatusListener;

import com.example.oud.api.LoggedInUser;
import com.example.oud.api.LoginResponse;
import com.example.oud.api.UpdatePasswordRequest;
import com.example.oud.api.UpdateProfileData;
import com.example.oud.connectionaware.ConnectionAwareRepository;
import com.example.oud.connectionaware.FailureSuccessHandledCallback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class UpdatePasswordRepository extends ConnectionAwareRepository {

    public MutableLiveData<LoggedInUser> getCurrentUser(String token){
        MutableLiveData<LoggedInUser> loggedInUserMutableLiveData = new MutableLiveData<>();
        Call<LoggedInUser> call = oudApi.getUserProfile(token);
        addCall(call).enqueue(new FailureSuccessHandledCallback<LoggedInUser>(this) {
            @Override
            public void onResponse(Call<LoggedInUser> call, Response<LoggedInUser> response) {
                super.onResponse(call,response);
                if(response.isSuccessful()){
                    loggedInUserMutableLiveData.setValue(response.body());
                }
            }

        });


        return loggedInUserMutableLiveData;
    }


    public MutableLiveData<LoginResponse> updatePassword(String token, UpdatePasswordRequest updatePasswordRequest, ConnectionStatusListener connectionStatusListener){
        MutableLiveData<LoginResponse> loggedInUserMutableLiveData = new MutableLiveData<>();
        Call<LoginResponse> call = oudApi.updatePassword(token,updatePasswordRequest);
        addCall(call).enqueue(new FailureSuccessHandledCallback<LoginResponse>(this,connectionStatusListener) {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onResponse(call,response);
                if(response.isSuccessful()){
                    loggedInUserMutableLiveData.setValue(response.body());
                }
            }

        });
        return loggedInUserMutableLiveData;
    }


    public void updateProfile(String token, UpdateProfileData data,ConnectionStatusListener connectionStatusListener ,MutableLiveData<String> errorMessage){
        Call<ResponseBody> call  =oudApi.updateProfile(token,data);
        addCall(call).enqueue(new FailureSuccessHandledCallback<ResponseBody>(this,connectionStatusListener){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                super.onResponse(call, response);
                if(!response.isSuccessful())
                    errorMessage.setValue(response.message());
            }
        });
    }

}
