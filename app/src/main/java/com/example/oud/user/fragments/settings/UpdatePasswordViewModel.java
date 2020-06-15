package com.example.oud.user.fragments.settings;

import androidx.lifecycle.MutableLiveData;

import com.example.oud.ConnectionStatusListener;
import com.example.oud.Constants;
import com.example.oud.api.LoggedInUser;
import com.example.oud.api.LoginResponse;
import com.example.oud.api.UpdatePasswordRequest;
import com.example.oud.api.UpdateProfileData;
import com.example.oud.connectionaware.ConnectionAwareViewModel;

public class UpdatePasswordViewModel extends ConnectionAwareViewModel<UpdatePasswordRepository> {


    public MutableLiveData<LoggedInUser> getCurrentUser(String token) {
        return mRepo.getCurrentUser(token);
    }

    public UpdatePasswordViewModel(){
        super(new UpdatePasswordRepository(), Constants.YAMANI_MOCK_BASE_URL);
    }


    public MutableLiveData<LoginResponse> updatePassword(String token , UpdatePasswordRequest updatePasswordRequest, ConnectionStatusListener connectionStatusListener){
        return mRepo.updatePassword(token,updatePasswordRequest,connectionStatusListener);
    }

    public void updateProfile(String token, UpdateProfileData data, ConnectionStatusListener connectionStatusListener , MutableLiveData<String> errorMessage){
        mRepo.updateProfile(token,data,connectionStatusListener,errorMessage);
    }
    @Override
    public void clearData() {
    }
}

