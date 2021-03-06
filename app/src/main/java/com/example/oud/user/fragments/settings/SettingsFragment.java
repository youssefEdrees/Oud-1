package com.example.oud.user.fragments.settings;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.oud.OudUtils;
import com.example.oud.R;
import com.example.oud.api.LoggedInUser;
import com.example.oud.api.ProfilePreview;
import com.example.oud.connectionaware.ConnectionAwareFragment;
import com.example.oud.user.UserActivity;
import com.example.oud.user.fragments.profile.ProfileFragment;
import com.example.oud.user.fragments.profile.ProfileViewModel;

public class SettingsFragment extends ConnectionAwareFragment<SettingsViewModel> {
    String myId;
    String token;

    ImageView profileImageView;
    TextView displayNameTextView;


public SettingsFragment(Activity activity){
    super(SettingsViewModel.class,
            R.layout.fragment_settings,
            activity.findViewById(R.id.progress_bar_user_activity),
            activity.findViewById(R.id.block_view),
            null);

}


    public static SettingsFragment newInstance(Activity activity) {
        return new SettingsFragment(activity);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myId = OudUtils.getUserId(getContext());
        token = OudUtils.getToken(getContext());

        Button viewMyProfileTest =view.findViewById(R.id.btn_view_my_profile_test);
        Button viewOtherProfileTest =view.findViewById(R.id.btn_view_other_profile_test);
        ConstraintLayout constraintLayout = view.findViewById(R.id.constraint_layout_profile_settings);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment.show(getActivity(),R.id.nav_host_fragment,OudUtils.getUserId(getContext()));
            }
        });


        profileImageView = view.findViewById(R.id.image_settings_profile);
        displayNameTextView = view.findViewById(R.id.text_settings_display_name);

        profileImageView.requestFocus();


        mViewModel.getProfile(myId,token).observe(getViewLifecycleOwner(), new Observer<ProfilePreview>() {
            @Override
            public void onChanged(ProfilePreview profilePreview) {
                displayNameTextView.setText(profilePreview.getDisplayName());
                String imageUrl = OudUtils.convertImageToFullUrl(profilePreview.getImages()[0]);
                OudUtils.glideBuilder(getActivity(),imageUrl).load(imageUrl).into(profileImageView);
            }
        });



        viewMyProfileTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment.show(getActivity(),R.id.nav_host_fragment, OudUtils.getUserId(getContext()));


            }
        });

        viewOtherProfileTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment.show(getActivity(),R.id.nav_host_fragment,"user1");


            }
        });

        PreferencesSettingsFragment fragment = PreferencesSettingsFragment.newInstance(getContext());
        getChildFragmentManager().beginTransaction().replace(R.id.container_setting_fragment,fragment).addToBackStack(null).commit();



    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

}
