package bases.commonmodel

import android.provider.SyncStateContract

import com.google.gson.annotations.SerializedName
import utilities.STATUS_LOGGED_IN
import utilities.STATUS_LOGGED_OUT

class Device(@field:SerializedName("device_id")
             val id: String, status: Boolean, @field:SerializedName("firebase_token")
             val firebaseToken: String) {
    @SerializedName("status")
    val status: Int

    init {
        this.status = if (status) STATUS_LOGGED_IN else STATUS_LOGGED_OUT
    }
}
