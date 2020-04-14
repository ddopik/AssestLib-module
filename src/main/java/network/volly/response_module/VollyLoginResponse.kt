package network.volly.response_module

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by ddopik..@_@
 */
class VollyLoginResponse : BaseResponse() {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null
    @SerializedName("data")
    @Expose
    var loginData: VollyLoginData? = null
    @SerializedName("code")
    @Expose
    var code: String? = null
}