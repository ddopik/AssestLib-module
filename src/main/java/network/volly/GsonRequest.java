
package network.volly;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import network.volly.response_module.DirectionResponse;

/**
 * https://developer.android.com/training/volley/request-custom.html
 * /**
 * Created by ddopik..@_@
 */

public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private Map<String, String> params;
    private final String TAG = getClass().getSimpleName();

    private boolean mIsDirectionRequest = false;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url     URL of the request to make
     * @param clazz   Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */

    public GsonRequest(int method,
                       String url,
                       Class<T> clazz,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener,
                       Map<String, String> headers,
                       Map<String, String> params) {

        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
        if (params != null)
            this.params = params;


        setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //extend the connection time out for for 25 sec and number of Retry
//        setRetryPolicy(new DefaultRetryPolicy(25000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public GsonRequest(int method,
                       String url,
                       Class<T> clazz,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener,
                       Map<String, String> headers,
                       Map<String, String> params, boolean isDirectionRequest) {

        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
        if (params != null)
            this.params = params;

        setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mIsDirectionRequest = isDirectionRequest;
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        if (params != null)
            return params;
        else
            return super.getParams();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            if (!response.headers.isEmpty()) {
                String contentType = response.headers.get("Content-Type");
                Log.d(TAG, "Content-Type: " + contentType);
//                if response is not application/json
//                retry
                if (!contentType.contains(VolleySingleton.CONTENT_TYPE)) {
                    Log.d(TAG, "Retry Api Call ");
                    if (!VolleySingleton.getInstance().retryRequest(this)) {
                        return Response.error(new ParseError(new UnsupportedEncodingException()));
                    }
                }
            }
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            //---------------Direction workaround-----begin--------------
            if (mIsDirectionRequest)
                DirectionResponse.jsonResponse = json;
            //---------------Direction workaround-----end--------------


            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));


        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        return super.setRetryPolicy(retryPolicy);
    }


}