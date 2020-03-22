package com.example.oud;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.oud.api.LoggedInUser;
import com.example.oud.api.OudApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


/*
 * A simple {@link Fragment} subclass.
 * Use the {@link MainLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainLoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";
    private Button toLoginBtn;
    private Button toSignupBtn;
    private Button connectWithFacebookBtn;
    private final String BASE_URL = "http://example.com";
    OudApi oudApi;

    // TODO: Rename and change types of parameters



    public MainLoginFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO: add a toolbar to this fragment
        //setToolbar();
        View v = inflater.inflate(R.layout.fragment_main_login, container, false);

        initializeViews(v);
        setButtonsOnClickListener();
        checkSavedToken();





        return v;
    }


    private void setToolbar(){
        ((MainActivity) getActivity()).getSupportActionBar().hide();

    }

    private void initializeViews(View v){
        toLoginBtn = v.findViewById(R.id.Btn_to_login_fragment);
        toSignupBtn = v.findViewById(R.id.Btn_to_signup_fragment);
        connectWithFacebookBtn = v.findViewById(R.id.Btn_to_facebook_login_fragment);


    }
    private void setButtonsOnClickListener(){

        toLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_mainLoginFragment_to_actualLoginFragment);
            }
        });

        toSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_mainLoginFragment_to_signupFragment);
            }
        });

        connectWithFacebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectWithOtherServicesDialogFragment dialog = new ConnectWithOtherServicesDialogFragment() ;
                dialog.show(getParentFragmentManager(),"a tag");
            }
        });

    }

    private void checkSavedToken(){
        //checks if there is a saved token and if it gets the user's profile
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        oudApi = retrofit.create(OudApi.class);


        SharedPreferences prefs = getContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        if(prefs.contains("token")){
            //there is a stored token in the shared preferences
            String token = prefs.getString("token","000000");
            Call<LoggedInUser> call = oudApi.getUserProfile(token);
            call.enqueue(new Callback<LoggedInUser>() {
                @Override
                public void onResponse(Call<LoggedInUser> call, Response<LoggedInUser> response) {
                    if(response.isSuccessful()){
                        //todo go to homepage
                    }

                }

                @Override
                public void onFailure(Call<LoggedInUser> call, Throwable t) {
                    // internet issue
                }
            });
        }


    }
}
