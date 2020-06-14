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

import com.example.oud.OudUtils;
import com.example.oud.R;
import com.example.oud.api.LoggedInUser;
import com.example.oud.connectionaware.ConnectionAwareFragment;

public class UpdateInfoFragment extends ConnectionAwareFragment<UpdatePasswordViewModel> {

    public UpdateInfoFragment(Activity activity){
        super(UpdatePasswordViewModel.class,
                R.layout.fragment_update_info,
                activity.findViewById(R.id.progress_bar_user_activity),
                activity.findViewById(R.id.block_view),
                null);
    }

    public static void show(FragmentActivity activity,
                            @IdRes int containerId) {

        FragmentManager manager = activity.getSupportFragmentManager();

        UpdateInfoFragment updateInfoFragment = new UpdateInfoFragment(activity);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(containerId, updateInfoFragment)
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
        mViewModel.getCurrentUser(OudUtils.getToken(getContext())).observe(getViewLifecycleOwner(), new Observer<LoggedInUser>() {
            @Override
            public void onChanged(LoggedInUser loggedInUser) {

            }
        });
    }
}
