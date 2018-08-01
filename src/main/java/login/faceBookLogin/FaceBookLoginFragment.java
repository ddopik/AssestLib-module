package login.faceBookLogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.networkmodule.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ddopik on 7/12/2017.
 */

public abstract class FaceBookLoginFragment extends android.support.v4.app.Fragment {


    private View mainView;

    public LoginButton facebook_button;
    ProgressDialog progress;
    private String facebook_id, f_name, m_name, l_name, gender, profile_image, full_name, email_id;
    //    /For facebook
    private CallbackManager callbackManager;


    public void intialView() {
        facebook_button = mainView.findViewById(R.id.connectWithFbButton);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(getActivity());

        mainView = inflater.inflate((getFacceBookButtomWidgetLayout() != 0) ? getFacceBookButtomWidgetLayout() : R.layout.facebook_login_fragment, container, false);
        facebook_button = mainView.findViewById((getFaceBookButtonId() != 0) ? getFaceBookButtonId() : R.id.connectWithFbButton);
        facebook_button.setFragment(this); /// Reburied for onSuccess callBack
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

                        Log.i("LoginActivity", response.toString());
                        try {
                            Log.e("json is ", object.toString());
                            Log.e("response is ", response.toString());
                            String email = object.getString("email");
                            String birthday = object.getString("birthday"); // 01/31/1980 format

                            email_id = object.getString("email");
                            gender = object.getString("gender");
                            String profile_name = object.getString("name");
                            long fb_id = object.getLong("id"); //use this for logout
//                           Start new activity or use this info in your project.

                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("task_shared_pref", MODE_PRIVATE).edit();
                            editor.putLong("fb_id", fb_id);
                            editor.apply();

                            Intent intent = new Intent(getActivity(), getHomeActivityName());
                            intent.putExtra("type", "facebook");
                            intent.putExtra("facebook_id", facebook_id);
                            intent.putExtra("facebook_access_token", getAccessToken());
                            intent.putExtra("f_name", f_name);
                            intent.putExtra("m_name", m_name);
                            intent.putExtra("l_name", l_name);
                            intent.putExtra("full_name", full_name);
                            intent.putExtra("profile_image", profile_image);
                            intent.putExtra("email_id", email_id);
                            intent.putExtra("gender", gender);
                            progress.dismiss();
                            OnFaceBookResponseComplete(object, intent);

                        } catch (Exception e) {
                            Log.e("FaceBookLoginFragment", "Error --->" + e.getMessage());
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
                OnFaceBookResponseCanceled();
                Toast.makeText(getActivity(), getResources().getString(R.string.login_canceled_facebooklogin), Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }

            @Override
            public void onError(FacebookException error) {
                OnFaceBookResponseError(error);
                Toast.makeText(getActivity(), getResources().getString(R.string.login_failed_facebooklogin), Toast.LENGTH_SHORT).show();
                Log.e("FaceBookLoginFragment", "Error ---->" + error.getMessage());
                progress.dismiss();
            }
        });

        intialView();
        intialListner();
        return mainView;
    }

    public void intialListner() {
        facebook_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "user_friends", "email"));
            }
        });
    }


    private String getAccessToken() {
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        AccessToken token = AccessToken.getCurrentAccessToken();
        return String.valueOf(token.getToken());
    }

    public abstract Class getHomeActivityName();

    public abstract void OnFaceBookResponseComplete(JSONObject object, Intent intent);

    public abstract void OnFaceBookResponseError(FacebookException error);

    public abstract void OnFaceBookResponseCanceled();

    public abstract int getFacceBookButtomWidgetLayout();

    public abstract int getFaceBookButtonId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //for facebook callback result.  this method calling the callback manger on current(Fragment/avtivity)
    // and send current intent request
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}




































