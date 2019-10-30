package utilities;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;


public final class VolleyLogger {
    private static final String TAG = "Volley Logger";

    public static String formatVolleyError(VolleyError error) {
        String formattedError = "";

        if (null == error.networkResponse) return TAG + " Network Response = null!!";
        formattedError += "NW Time : " + Utilities.formatTimeInMilliSeconds(error.networkResponse.networkTimeMs);

        formattedError += ", Status Code: " + error.networkResponse.statusCode;
        if (error.getMessage() != null) formattedError += ", Error Message: " + error.getMessage();
        formattedError += ", Not Modified: " + error.networkResponse.notModified;
        formattedError += ", Headers : " + HttpHeaderParser.parseCharset(error.networkResponse.headers);

        return formattedError;
    }

    public static String formatRequestLog(Request request) {

        String s = "";
        if (request.hasHadResponseDelivered()) s = ",Req. Sequence: " + request.getSequence();
        return ", Method: " + request.getMethod() + ", Url: " + request.getUrl() + ", Priority: " + request.getPriority() + s;
    }

    public static String formatRequestLogWithHeaders(Request request) {
        String headers = "None";
        try {
            if (!request.getHeaders().isEmpty()) {
                headers = HttpHeaderParser.parseCharset(request.getHeaders());
            }
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
            Log.e("Volley Logger", "Auth Failure %d %d" + authFailureError.toString() + request.getUrl());
        }
        return formatRequestLog(request) + ", Headers: " + headers;
    }

    /**
     * Utility Method for logging request headers or paramsz
     *
     * @param messageName        To show it in the log
     * @param paramsOrHeadersMap Request Headers or Parameters
     */
    private void logMap(String messageName, Map<String, String> paramsOrHeadersMap) {
        StringBuilder readableMapString = new StringBuilder("");
        for (String key : paramsOrHeadersMap.keySet()) {
            readableMapString.append(key.trim()).append(" : ").append(paramsOrHeadersMap.get(key).trim());
            readableMapString.append("&");
        }
        Log.i(TAG, messageName + ": " + readableMapString);
    }
}
