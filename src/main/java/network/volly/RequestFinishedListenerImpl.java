package network.volly;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import utilities.VolleyLogger;


public class RequestFinishedListenerImpl implements RequestQueue.RequestFinishedListener {
    @Override
    public void onRequestFinished(Request request) {
        Log.i("RequestFinished", " ***** " + VolleyLogger.formatRequestLogWithHeaders(request) + "*****");
    }

}
