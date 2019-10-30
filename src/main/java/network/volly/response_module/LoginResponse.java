package network.volly.response_module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ddopik..@_@
 */
public class LoginResponse extends BaseResponse {

    @SerializedName("firstLogin")
    public int firstLogin;


    @SerializedName("token")
    public String token;

    @SerializedName("activated")
    public int activated = -100; //put it to -100 to avoid default 0 value which is used with inactive account

    @SerializedName("hasPromotion")
    public String hasPromotion;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "firstLogin=" + firstLogin +
                ", token='" + token + '\'' +
                ", activated=" + activated +
                ", hasPromotion='" + hasPromotion + '\'' +
                '}';
    }
}
