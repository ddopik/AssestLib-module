package network.volly;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import network.volly.response_module.BaseApiResponse;
import network.volly.response_module.BaseResponse;
import network.volly.response_module.CreateOrderResponse;
import network.volly.response_module.DirectionResponse;
import network.volly.response_module.LoginResponse;

/**
 * Created by ddopik..@_@
 */

public class VolleySingleton {
    public static final String CONTENT_TYPE = "application/json";
    public static final String ERROR_MESSAGE_KEY = "message";
    public static final String BASEURL = "http://v3.getvoo.com/";
    public static final String LOGIN = "api/login";
    public static final String UPDATE_PROFILE = "api/accounts/profile";

    public static final String DIRECTION_URL = "https://maps.googleapis.com/maps/api/directions/json";
    public static final int BAD_REQUEST_RESPONSE_CODE = 400;
    //-------------Constant---Start
    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";
    private final static int MAX_RETRY_COUNT = 2;
    public static int retryCount = 1;
    //-------------TLS------end----
    private static VolleySingleton mInstance = null;
    private final String TAG = getClass().getSimpleName();
    //-------------TLS------begin----
    HttpStack stack = null;
    private RequestQueue mRequestQueue;
    private String mToken = "";
    //-------------Constant---End


    private VolleySingleton(Context context) {

        ////////////////
        //-------------TLS------begin----
        if (Build.VERSION.SDK_INT >= 9) {
            try {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    // Use a socket factory that removes sslv3 and add TLS1.2
//                    stack = new HurlStack(null, new TLSSocketFactory());
                } else {
                    stack = new HurlStack();
                }
            } catch (Exception e) {
                stack = new HurlStack();
                Log.i("NetworkClient", "can no create custom socket factory");
            }
        }
//        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext(), stack);
//        The FORT Mobile SDK allows Merchants to securely integrate the payment functions.
        //////////////////todo this block should be activated if you are integrated ---FORT Mobile SDK---

        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        //-------------TLS------end----
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (null == mInstance)
            mInstance = new VolleySingleton(context);
        return mInstance;
    }

    //this is so you don't need to pass context each time
    public static synchronized VolleySingleton getInstance() {
        if (null == mInstance) {
            throw new IllegalStateException(VolleySingleton.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return mInstance;
    }


    public static boolean checkResponse(BaseResponse response) {
        if (response.status.toLowerCase().equals(SUCCESS_STATUS))
            return true;
        else if (response.status.toLowerCase().equals(ERROR_STATUS)) {
//            activity.showToast(response.message);
            Log.e("checkResponse", "" + response.message);
        }

        return false;
    }


    // Handle back bad requests with code 400
    public static String resolveBadResponse(VolleyError error) {
        String json = null, systemJson = null;
        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            switch (response.statusCode) {
                case VolleySingleton.BAD_REQUEST_RESPONSE_CODE:
                    json = systemJson = new String(response.data);
                    json = getErrorMessage(json, ERROR_MESSAGE_KEY);
                    if (json == null)
                        Log.e("VolleySingleton", "Error ----> unResolved base APi response");
                    else
                        Log.e("VolleySingleton", "Error ---->base APi response as " + json);
                    break;

            }
        }
        return systemJson;

    }

    public static String getErrorMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }


    /**
     * Cancels all the request in the Volley queue with given tag
     *
     * @param tag associated with the Volley requests to be cancelled
     */
    public void cancelAllRequests(String tag) {
        if (mRequestQueue != null && tag != null && !TextUtils.isEmpty(tag)) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * Cancel All Requests
     */
    public void cancelAllRequests() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    /*mh if (request.getUrl().contains(LOAD_INITIAL_DATA)) {
                        Logger.debug(TAG, "Not Cancelling Initial Data Request: " + request.getUrl());
                        return false;
                    }*/
                    Log.e(TAG, "Cancelling Requests: " + request.getUrl());
                    return true;
                }
            });
        }
    }


    /**
     * Adds a request to the Volley request queue with a given tag
     *
     * @param request is the request to be added
     * @param tag     is the tag identifying the request
     */
    private Request<?> addRequest(Request<?> request, String tag) {
        request.setTag(tag);
//        request.setShouldCache(true);
        return addRequest(request);
    }


    /**
     * Adds a request to the Volley request queue
     *
     * @param request is the request to be added
     */
    private Request<?> addRequest(Request<?> request) {
        mRequestQueue.addRequestFinishedListener(new RequestFinishedListenerImpl());
        return mRequestQueue.add(request);
    }


    //    Retry Request
    // in case callback was broken UnSpecified Content Type ....
    public boolean retryRequest(Request request) {
        if (retryCount >= MAX_RETRY_COUNT) {
            cancelAllRequests(request.getUrl());
            retryCount = 1;
            return false;
        }
        retryCount++;
        addRequest(request);
        return true;
    }


    public Request<?> makeGsonRequest(int method, Class<?> clazz, String url, Response.Listener<?> listener, Response.ErrorListener errorListener, Map<String, String> headers, Map<String, String> params) {
//        mRequestQueue.cancelAll(TAG)
        GsonRequest gsonRequest = new GsonRequest(method, url, clazz, listener, errorListener, headers, params);
//        Logging the request parameters
        if (params != null && !params.isEmpty())
            logMap("params", params);
        if (headers != null && !headers.isEmpty())
            logMap("headers", headers);
//Use the url as the request tag to be able to remove a specific request at anytime
        return addRequest(gsonRequest, url);
    }


    ////////////////////////////This will be used only for googlemaps directions///////////////////////////////////

    public Request<?> makeGsonRequest(int method, Class<?> clazz, String url, Response.Listener<?> listener,
                                      Response.ErrorListener errorListener, Map<String, String> headers,
                                      Map<String, String> params, boolean isDirection) {

        GsonRequest gsonRequest = new GsonRequest(method,
                url, clazz, listener, errorListener, headers, params, isDirection);


//        Logging the request parameters
        if (params != null && !params.isEmpty())
            logMap("params", params);
        if (headers != null && !headers.isEmpty())
            logMap("headers", headers);
//Use the url as the request tag to be able to remove a specific request at anytime
        return addRequest(gsonRequest, url);
    }

    /**
     * Utility Method for logging request headers or params
     *
     * @param messageName        To show it in the log
     * @param paramsOrHeadersMap Request Headers or Parameters
     */
    private void logMap(String messageName, Map<String, String> paramsOrHeadersMap) {
        try {
            StringBuilder readableMapString = new StringBuilder("");
            for (String key : paramsOrHeadersMap.keySet()) {


                readableMapString.append(key.trim()).append("=").append(paramsOrHeadersMap.get(key).trim());
                readableMapString.append("&");
            }
        } catch (Exception e) {
            //// TODO: 11/11/2017 this method Crashes when first time Login with facebook case No accese token returned
            Log.e("VolleySingleton", "Error in VolleySingleton ---> logMap()" + e.getMessage());
        }

    }
    ////////////////////////////<//This will be used only for googlemaps directions>///////////////////////////////////


    public String getToken() {
        return (!TextUtils.isEmpty(mToken)) ? mToken : "";
    }

    public void setToken(String token) {
        mToken = token;
    }

    private Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer{" + getToken() + "}");
        return headers;
    }


    public Request getDirection(final BaseApiResponse<DirectionResponse> apiResponse, String parameters) {
        return makeGsonRequest(Request.Method.GET, DirectionResponse.class, DIRECTION_URL + parameters, new Response.Listener<DirectionResponse>() {
            @Override
            public void onResponse(DirectionResponse response) {
                apiResponse.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                apiResponse.onFailure(error);
            }
        }, null, null, true);

    }


    public Request createOrder(final BaseApiResponse<CreateOrderResponse> apiResponse, Map<String, String> parameters) {
        Log.e(TAG, "createOrder()---Parameters--->" + parameters.toString());
//        activity.showProgressDialog();
//        setToken(VOOPrefManager.getUserToken(activity));
        return makeGsonRequest(Request.Method.POST, CreateOrderResponse.class, BASEURL + "",
                new Response.Listener<CreateOrderResponse>() {
                    @Override
                    public void onResponse(CreateOrderResponse response) {
                        apiResponse.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        apiResponse.onFailure(error);
                    }
                },
                getRequestHeaders(),
                parameters
        );
    }

    public Request login(final BaseApiResponse<LoginResponse> apiResponse,
                         Map<String, String> parameters) {

        return makeGsonRequest(Request.Method.POST,LoginResponse.class, BASEURL + LOGIN, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                apiResponse.onSuccess(response);
                Log.e("NetworkManager", "login()----> Response is--->" + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                apiResponse.onFailure(error);
            }
        }, null, parameters);

    }

    //---------------------Upload image---------begin----------------
//    ImageUtils.compressImage(getBaseActivity(), mImgPath, ConstUtls.AVATAR_MAX_WIDTH, ConstUtls.AVATAR_MAX_HEIGHT);
//    uploadImage(FileUtils.TEMP_FILES + "/" + ImageUtils.getImgName(mImgPath));
    public void uploadImage(String imagePath, Response.Listener<BaseResponse> listener, Response.ErrorListener errorListener, String imageParameterName) {

        UploadImageRequest uploadImageRequest = new UploadImageRequest(Request.Method.POST,
                BASEURL + UPDATE_PROFILE, "",
                BaseResponse.class, listener, errorListener, getRequestHeaders(), imagePath, getImageBytes(imagePath), imageParameterName);

        addRequest(uploadImageRequest);
    }

    byte[] getImageBytes(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bitmapData = byteArrayOutputStream.toByteArray();

        return bitmapData;
    }

    //---------------------Upload image---------end----------------

}