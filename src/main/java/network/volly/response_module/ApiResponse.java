package network.volly.response_module;

import com.android.volley.VolleyError;
/**
 * Created by ddopik..@_@
 */

public interface ApiResponse<T> {
    void onSuccess(T response);

    void onFailure(VolleyError error);
}
