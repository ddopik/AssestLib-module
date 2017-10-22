package com.example.networkmodule.simpleJsonRequest;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by ddopi on 7/10/2017.
 * <p>
 * if you parent need field to be intialized before abstract method in the child being impleented
 * call parent constructur with super(required fiels,required fiels,required fiels);
 * 1---case when parent constructor calling abstract method  through it's constructor
 */

public abstract class Url_JsonRequest {


    private RequestQueue requestQueue;
    private Context activityContext;
    private String requsetUrl;
    private String tag="Url_JsonRequest";

    public Url_JsonRequest(Context context) {
        activityContext = context;
    }

    public abstract String getUrl();

    public abstract PresenterRequest getPresenterRequest();

    public RequestQueue getRequestQueue() {
        return requestQueue = Volley.newRequestQueue(activityContext);
    }


    public void sentRequest(boolean casheRequest) {

        requsetUrl = this.getUrl().replaceAll("\\s+", ""); ///remove unnessasary spaces



        if(casheRequest==true && casheRequset(requsetUrl) !=null) {
            getPresenterRequest().jsonRequest(casheRequset(requsetUrl));
        }else
        {
            lifeRequest(getPresenterRequest(), requsetUrl);
        }
    }


    private JSONObject casheRequset(String url)
    {
        Cache cache = getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        JSONObject obj=new JSONObject();
        if (entry != null) {
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    XmlToJson xmlToJson = new XmlToJson.Builder(data).build();
                    obj=new JSONObject(xmlToJson.toString());
                    Log.e("NewsNetWorkController","Cashe Returned = "+xmlToJson.toString());
                } catch (Exception e) {
                    Log.e("NewsNetWorkController", "No Cashe Found" + e.getMessage());
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.e("NewsNetWorkController", "Cashe Error---->" + e.getMessage());
            }

        }
        return obj;
    }


    public void lifeRequest(PresenterRequest presenterRequest,final String mLoginUrl)
    {
        requestQueue = getRequestQueue();
        final PresenterRequest rPresenterRequest=presenterRequest;

        StringRequest stringRequest = new StringRequest(Request.Method.POST,mLoginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    rPresenterRequest.jsonRequest(new JSONObject(response));  ///recived action from presenter
                    Log.e(tag, "Json converted----------->" + new JSONObject(response).toString());
                    Log.e(tag, "url is---->" + mLoginUrl);

                } catch (Exception e) {
                    Log.e(tag, "Error---->: \"" + e.getMessage() + "\"");
                    Log.e(tag, "Could not parse Returned JSON as Response---->: \"" + response + "\"");
                    Log.e(tag, "url is---->" + mLoginUrl);
                    rPresenterRequest.jsonRequest(null);  ///recived action from presenter
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Url_JsonRequest", "error with --->" + error.toString());
                rPresenterRequest.jsonRequest(null);  ///recived action from presenter
            }
        }) {

        };
        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy); //configure retry policy. Give a large timeout and try,--->avoide timeOut exception
        requestQueue.add(stringRequest);


    }

    public interface PresenterRequest {
        //// implement this interFace an handle recived obj from Request
         void jsonRequest(JSONObject jsonObjectobj);
    }
}
