package permations

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import utilities.PrefUtils


abstract class k_PermationController {


    companion object {

        @TargetApi(Build.VERSION_CODES.M)
        public fun checkPermission(context: Context, permation: String, listner: PermissionAskListener) {
/*
        * If permission is not granted
        * */
            if (shouldAskPermission(context, permation)) {
/*
            * If permission denied previously
            * */
                if ((context as Activity).shouldShowRequestPermissionRationale(permation)) {
                    listner.onPermissionPreviouslyDenied();
                } else {
                    /*
                    * Permission denied or first time requested
                    * */
                    if (PrefUtils.isFirstTimeAskingPermission(context, permation)) {
                        PrefUtils.firstTimeAskingPermission(context, permation, false);
                        listner.onPermissionAsk();
                    } else {
                        /*
                        * Handle the feature without permission or ask user to manually allow permission
                        * */
                        listner.onPermissionDisabled();
                    }
                }
            } else {
                listner.onPermissionGranted();
            }
        }



          fun shouldAskPermission() :Boolean{
            return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        }


        private  fun shouldAskPermission (context :Context ,permission : String ) :Boolean{
            if (shouldAskPermission()) {
                val permissionResult  = ActivityCompat.checkSelfPermission(context, permission);
                if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                    return true
                }
            }
            return false
        }

    }

    /*
    * Callback on various cases on checking permission
    *
    * 1.  Below M, runtime permission not needed. In that case onPermissionGranted() would be called.
    *     If permission is already granted, onPermissionGranted() would be called.
    *
    * 2.  Above M, if the permission is being asked first time onPermissionAsk() would be called.
    *
    * 3.  Above M, if the permission is previously asked but not granted, onPermissionPreviouslyDenied()
    *     would be called.
    *
    * 4.  Above M, if the permission is disabled by device policy or the user checked "Never ask again"
    *     check box on previous request permission, onPermissionDisabled() would be called.
    * */
    public interface PermissionAskListener {

        /*
            * Callback to ask permission
            * */
        fun onPermissionAsk()

        /*
                * Callback on permission denied
                * */
        fun onPermissionPreviouslyDenied();

        /*
                * Callback on permission "Never show again" checked and denied
                * */
        fun onPermissionDisabled()

        /*
                * Callback on permission granted
                * */
        fun onPermissionGranted()
    }
}

