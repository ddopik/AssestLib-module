package com.example.networkmodule.faceBookLogin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.networkmodule.R;
import com.example.networkmodule.R2;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.R.id.progress;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ddopi on 7/12/2017.
 */

public abstract  class FaceBookLoginFragment extends android.support.v4.app.Fragment {


    private View mainView;
    @BindView(R2.id.connectWithFbButton)
    public LoginButton facebook_button;
    ProgressDialog progress;
    private String facebook_id, f_name, m_name, l_name, gender, profile_image, full_name, email_id;
    //    /For facebook
    private CallbackManager callbackManager;
    private Unbinder unbinder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(getActivity());

        mainView = inflater.inflate(R.layout.facebook_login_fragment, container, false);
        unbinder=ButterKnife.bind(this,mainView);
//        try {
//            getSupportActionBar().hide();
//        } catch (NullPointerException e) {
//
//        }
        facebook_button = (LoginButton) mainView.findViewById(R.id.connectWithFbButton);
        facebook_button.setFragment(this); /// Requried for onSuccess callBack
        progress = new ProgressDialog(getActivity());
        progress.setMessage(getActivity().getString(R.string.please_wait_facebooklogin));
        progress.setIndeterminate(false);
        progress.setCancelable(false);

        facebook_id = f_name = m_name = l_name = gender = profile_image = full_name = email_id = "";
        //for facebook

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        progress.show();
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                try {
                                    Log.e("json is ", object.toString());
                                    Log.e("response is ", response.toString());
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday"); // 01/31/1980 format

                                    email_id = object.getString("email");
                                    gender = object.getString("gender");
                                    String profile_name = object.getString("name");
                                    long fb_id = object.getLong("id"); //use this for logout
//                                    Start new activity or use this info in your project.


                                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("task_shared_pref", MODE_PRIVATE).edit();
                                    editor.putLong("fb_id", fb_id);
                                    editor.apply();


                                    Intent i = new Intent(getActivity(),getHomeActivityName());
                                    i.putExtra("type", "facebook");
                                    i.putExtra("facebook_id", facebook_id);
                                    i.putExtra("f_name", f_name);
                                    i.putExtra("m_name", m_name);
                                    i.putExtra("l_name", l_name);
                                    i.putExtra("full_name", full_name);
                                    i.putExtra("profile_image", profile_image);
                                    i.putExtra("email_id", email_id);
                                    i.putExtra("gender", gender);
                                    progress.dismiss();
                                    startActivity(i);
                                    getActivity().finish();

                                } catch (Exception e) {
                                    Log.e("FaceBookLoginFragment", "1---error --->" + e.getMessage());
                                    progress.dismiss();
                                }

                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getActivity(), getResources().getString(R.string.login_canceled_facebooklogin), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.login_failed_facebooklogin), Toast.LENGTH_SHORT).show();
                        Log.e("FaceBookLoginFragment", "2--error ---->" + error.getMessage());
                        progress.dismiss();
                    }
                });


        return mainView;
    }

    @OnClick(R2.id.connectWithFbButton)
    public void setFacebook_button_acction(View view)
    {
        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "user_friends", "email"));
    }

    public abstract Class getHomeActivityName();

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    //for facebook callback result.  this method calling the callback manger on current(Fragment/avtivity)
    // and send current intent request
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }





}




































