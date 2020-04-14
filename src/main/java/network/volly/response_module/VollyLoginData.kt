package network.volly.response_module

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VollyLoginData {

    @SerializedName("user_data")
    @Expose
    var userData: VollyLoginUserData? = null
    @SerializedName("attend_status")
    @Expose
    var attendStatus: VollyAttendStatus? = null
}