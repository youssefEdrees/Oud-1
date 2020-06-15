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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.oud.ConnectionStatusListener;
import com.example.oud.OudUtils;
import com.example.oud.R;
import com.example.oud.api.LoggedInUser;
import com.example.oud.api.UpdateProfileData;
import com.example.oud.connectionaware.ConnectionAwareFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class UpdateInfoFragment extends ConnectionAwareFragment<UpdatePasswordViewModel> {

    EditText displayNameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    Button updateInfoButton;
    DatePicker dateOfBirthDatePicker;
    RadioGroup genderRadioGroup;
    RadioButton maleRadioButton;
    RadioButton femaleRadioButton;


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
        initializeViews(view);
        setUpdateInfoButtonOnClickListener();
        mViewModel.getCurrentUser(OudUtils.getToken(getContext())).observe(getViewLifecycleOwner(), new Observer<LoggedInUser>() {
            @Override
            public void onChanged(LoggedInUser loggedInUser) {
                displayNameEditText.setText(loggedInUser.getDisplayName());
                emailEditText.setText(loggedInUser.getEmail());
                if(loggedInUser.getGender().equals("M")){
                    maleRadioButton.performClick();
                }
                else{
                    femaleRadioButton.performClick();
                }

                Date date1= null;
                try {
                    date1 = new SimpleDateFormat("yyyy-MM-dd").parse(loggedInUser.getBirthDate());

                } catch (ParseException e) {
                    e.printStackTrace();

                }

                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                cal.setTime(date1);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                dateOfBirthDatePicker.updateDate(year,month,day);
            }
        });
    }

    private void setUpdateInfoButtonOnClickListener(){
        updateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionStatusListener connectionStatusListener = new ConnectionStatusListener() {
                    @Override
                    public void onConnectionSuccess() {
                        Toast.makeText(getContext(),"profile updated",Toast.LENGTH_LONG).show();
                        getParentFragmentManager().popBackStack();
                    }

                    @Override
                    public void onConnectionFailure() {

                    }
                };

                MutableLiveData<String> errorMessage = new MutableLiveData<>();
                errorMessage.observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
                    }
                });
                if(displayNameEditText.getText().toString().length()<1){
                    displayNameEditText.setError("please enter display name");}
                else if(!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText()).matches()){
                    emailEditText.setError("please enter a valid email address");
                }
                else if(passwordEditText.getText().length()<8){
                    passwordEditText.setError("please enter your current password");
                }
                else {
                    String gender = "F";
                    if(maleRadioButton.isChecked())
                        gender ="M";

                    String month =dateOfBirthDatePicker.getMonth()+"";
                    if(month.length()==1)
                        month= "0"+month;

                    String day =dateOfBirthDatePicker.getDayOfMonth()+"";
                    if(day.length()==1)
                        day= "0"+day;
                    String dateOfBirth = dateOfBirthDatePicker.getYear()+"-"+month+"-"+day;

                    UpdateProfileData data = new UpdateProfileData(emailEditText.getText().toString(),passwordEditText.getText().toString(),gender, dateOfBirth,null,displayNameEditText.getText().toString());
                    mViewModel.updateProfile(OudUtils.getToken(getContext()),data ,connectionStatusListener , errorMessage);

                }
            }
        });

    }
    private void initializeViews(View view){
        displayNameEditText = view.findViewById(R.id.txt_update_display_name);
        emailEditText = view.findViewById(R.id.txt_update_email);
        passwordEditText = view.findViewById(R.id.txt_update_info_password);
        updateInfoButton =  view.findViewById(R.id.btn_update_profile);
        dateOfBirthDatePicker= view.findViewById(R.id.date_update);
        genderRadioGroup= view.findViewById(R.id.radio_group_update_gender);
        maleRadioButton = view.findViewById(R.id.radio_button_update_male);
        femaleRadioButton = view.findViewById(R.id.radio_button_update_female);
    }
}
