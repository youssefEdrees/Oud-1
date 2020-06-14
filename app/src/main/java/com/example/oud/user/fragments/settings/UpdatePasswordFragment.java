package com.example.oud.user.fragments.settings;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oud.ConnectionStatusListener;
import com.example.oud.OudUtils;
import com.example.oud.R;
import com.example.oud.api.LoginResponse;
import com.example.oud.api.UpdatePasswordRequest;
import com.example.oud.connectionaware.ConnectionAwareFragment;
import com.example.oud.user.fragments.profile.ProfileFragment;

public class UpdatePasswordFragment extends ConnectionAwareFragment<UpdatePasswordViewModel> {
    Button updatePasswordButton;
    EditText oldPasswordText;
    EditText newPasswordText;
    EditText newPasswordConfirmText;

    public UpdatePasswordFragment(Activity activity){
        super(UpdatePasswordViewModel.class,
                R.layout.fragment_update_password,
                activity.findViewById(R.id.progress_bar_user_activity),
                activity.findViewById(R.id.block_view),
                null);
    }


    public static void show(FragmentActivity activity,
                            @IdRes int containerId) {

        FragmentManager manager = activity.getSupportFragmentManager();

        UpdatePasswordFragment updatePasswordFragment = new UpdatePasswordFragment(activity);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(containerId, updatePasswordFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updatePasswordButton = view.findViewById(R.id.btn_update_password);
        oldPasswordText = view.findViewById(R.id.txt_old_password);
        newPasswordText = view.findViewById(R.id.txt_new_password);
        newPasswordConfirmText = view.findViewById(R.id.txt_new_password_confirm);

        hideProgressBar();
        unBlockUi();


        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = oldPasswordText.getText().toString();
                String newPassword = newPasswordText.getText().toString();
                String newPasswordConfirm = newPasswordConfirmText.getText().toString();

                if(oldPassword.length()<8){
                    oldPasswordText.setError("please enter full password");
                }
                else if(newPassword.length()<8){
                    newPasswordText.setError("password must be at least 8 characters");
                }
                else if(newPasswordConfirm.length()<8){
                    newPasswordText.setError("password and password confirmation don't match");
                }
                else{
                    ConnectionStatusListener connectionStatusListener = new ConnectionStatusListener() {
                        @Override
                        public void onConnectionSuccess() {
                            Toast.makeText(getContext(),"password updated",Toast.LENGTH_LONG).show();
                            getParentFragmentManager().popBackStack();
                        }
                        @Override
                        public void onConnectionFailure() {
                            Toast.makeText(getContext(),"password not updated",Toast.LENGTH_LONG).show();
                        }
                    };
                    UpdatePasswordRequest updatePasswordRequest= new UpdatePasswordRequest(oldPassword,newPassword,newPasswordConfirm);
                    mViewModel.updatePassword(OudUtils.getToken(getContext()),updatePasswordRequest,connectionStatusListener).observe(getViewLifecycleOwner(), new Observer<LoginResponse>() {
                        @Override
                        public void onChanged(LoginResponse loginResponse) {
                            OudUtils.saveUserData(getContext(),loginResponse.getToken(),loginResponse.getUser().get_id());
                        }
                    });
                }

            }
        });




    }
}
