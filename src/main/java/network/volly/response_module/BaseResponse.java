package network.volly.response_module;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.ParameterizedType;

/**
 * Created by ddopik..@_@
 */
public class BaseResponse {


    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;


}
