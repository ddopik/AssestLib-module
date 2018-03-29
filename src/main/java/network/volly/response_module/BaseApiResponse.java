package network.volly.response_module;

import android.util.Log;

import com.android.volley.VolleyError;
public abstract class BaseApiResponse<T> implements ApiResponse<T> {

    @Override
    public void onFailure(VolleyError error) {
        Log.e("BaseApiResponse", "onFailure " + error.getMessage());
    }

}
