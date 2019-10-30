package network.volly;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import utilities.ImageUtils;

//import com.nilecode.vooclient.utilities.ImageUtils;

/**
 * Created by ddopik..@_@.
 */
public class UploadImageRequest<T> extends JsonRequest<T> {

    private final String TAG = getClass().getSimpleName();
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    //---------------Image parameters----------
    String mimeType;
    DataOutputStream dos = null;
    String lineEnd = "\r\n";
    String boundary = "apiclient-" + System.currentTimeMillis();
    String twoHyphens = "--";
    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 1024 * 1024;


    public static String imagePath;
    public static byte[]  bitmapData;

    private String mImageParameterName ;


    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url     URL of the request to make
     * @param clazz   Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public UploadImageRequest(int method, String url, String requestBody,
                              Class<T> clazz,
                              Response.Listener<T> listener,
                              Response.ErrorListener errorListener,
                              Map<String, String> headers, String imagePath, byte[] bitmapData, String imageParameterName) {

        super(method, url, requestBody, listener, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;

        //mh change this to be in build config
        setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        this.imagePath = imagePath;
        this.bitmapData = bitmapData;
        this.mImageParameterName = imageParameterName;
        this.mimeType = "multipart/form-data;boundary=" + boundary;


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
                    Log.e(TAG, "Retry");
                    if (!VolleySingleton.getInstance().retryRequest(this)) {
                        return Response.error(new ParseError(new UnsupportedEncodingException()));
                    }
                }
            }
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }

    }

    //-----Image functions------------

    @Override
    public String getBodyContentType() {
        return mimeType;
    }


    @Override
    public byte[] getBody()  {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        dos = new DataOutputStream(bos);
        try {
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            /*dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\""
                    + ImageUtils.getImgName(imagePath) + "\"" + lineEnd);*/

            dos.writeBytes("Content-Disposition: form-data; name=\""+mImageParameterName+"\";filename=\""
                    + ImageUtils.getImgName(imagePath)  + "\"" + lineEnd);

            dos.writeBytes(lineEnd);
            ByteArrayInputStream fileInputStream = new ByteArrayInputStream(bitmapData);
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            return bos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmapData;
    }
    //-----Image functions------------
}
