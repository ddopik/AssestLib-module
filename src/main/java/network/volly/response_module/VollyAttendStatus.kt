package network.volly.response_module

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VollyAttendStatus {
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("msg")
    @Expose
    var msg: String? = null
}