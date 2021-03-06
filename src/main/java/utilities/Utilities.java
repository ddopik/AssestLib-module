package utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.networkmodule.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ddopik @_@....
 */
public class Utilities {

    /**
     * Convert arabic number to english number "," NOT added here
     */
    private static final String arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9";

    /**
     * Converts density independent pixels to pixels.
     *
     * @param dp Dp to convert.
     * @return Pixel value.
     */
    public static float convertDpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * Converts pixels to density independent pixels.
     *
     * @param px Pixels to convert.
     * @return Dp value.
     */
    public static float convertPxToDp(float px) {
        return px / Resources.getSystem().getDisplayMetrics().density;
    }

    public static String formatTimeInMilliSeconds(long timeInMilliSeconds) {
        Date date = new Date(timeInMilliSeconds);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
        return formatter.format(date);
    }

    public static String getMacAddress(Context context) {
        WifiManager wimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String macAddress = wimanager.getConnectionInfo().getMacAddress();
        if (macAddress == null) {
            Log.e(context.getClass().getSimpleName(), "Device don't have mac address or wi-fi is disabled");
            macAddress = "";
        }
        return macAddress;
    }


//
//    public static void sendEmail(String email, BaseActivity activity) {
//        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//        emailIntent.setType("message/rfc822");
//        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
//        try {
//            activity.startActivity(Intent.createChooser(emailIntent, activity.getString(R.string.contact_us_email_client_chooser_title)));
//        } catch (android.content.ActivityNotFoundException ex) {
//            activity.showToast(activity.getString(R.string.contact_us_error_no_email_client));
//        }
//    }

    public static void sendEmail(String email, Context activity) {
        Resources resources = activity.getResources();

        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
//        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(content)));

        ///for mnail body
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.client_email_feedback_subject));
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.setType("message/rfc822");

        PackageManager pm = activity.getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");


        Intent openInChooser = Intent.createChooser(emailIntent, resources.getString(R.string.send_feed_back));

        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if (packageName.contains("android.gm")) {
                emailIntent.setPackage(packageName);
            } else if (packageName.contains("android.email")) {
                emailIntent.setPackage(packageName);
            }
// else if(packageName.contains("twitter") || packageName.contains("facebook") || packageName.contains("mms") || packageName.contains("android.gm")) {
//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
//                intent.setAction(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                if(packageName.contains("twitter")) {
//                    intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_twitter));
//                } else if(packageName.contains("facebook")) {
            // Warning: Facebook IGNORES our text. They say "These fields are intended for users to express themselves. Pre-filling these fields erodes the authenticity of the user voice."
            // One workaround is to use the Facebook SDK to post, but that doesn't allow the user to choose how they want to share. We can also make a custom landing page, and the link
            // will show the <meta content ="..."> text from that page with our link in Facebook.
//                    intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_facebook));
//                } else if(packageName.contains("mms")) {
//                    intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_sms));
//                } else if(packageName.contains("android.gm")) { // If Gmail shows up twice, try removing this else-if clause and the reference to "android.gm" above
//                    intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(resources.getString(R.string.share_email_gmail)));
//                    intent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.share_email_subject));
//                    intent.setType("message/rfc822");
//                }

//                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
//            }
        }

        // convert intentList to array
        LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);

        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        activity.startActivity(openInChooser);
    }

    public static void showHidePassword(EditText password, View showIcon, View hideIcon, boolean shouldShowPassword) {
        showIcon.setVisibility(View.VISIBLE);
        hideIcon.setVisibility(View.INVISIBLE);
        if (shouldShowPassword)
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        else
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        password.setSelection(password.getText().length());
    }

    public static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    /**
     * /Convert arabic number to english number "," added here
     */
    public static String getUSNumber(String Numtoconvert) {

        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        try {
            if (Numtoconvert.contains("٫"))
                Numtoconvert = formatter.parse(Numtoconvert.split("٫")[0].trim()) + "." + formatter.parse(Numtoconvert.split("٫")[1].trim());
            else
                Numtoconvert = formatter.parse(Numtoconvert).toString();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            Log.e("Utilities", "Could not convert Arabic number" + e.getMessage());
        }
        return Numtoconvert;
    }


    /**
     * getting instance of the giving String
     * Note that you need to provide the fully qualified name of the class for the class loader to find it.
     * I.e., if 'class' is actually in some package, you need to do forName("your.package.A") for it to work.
     */
    public static Class<?> getClassInstance(String className) throws ClassNotFoundException {

        return Class.forName(className);
    }

    public static void changeAppLanguage(Context context, String localType) {
        Locale locale = new Locale(localType);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);


    }

    public static void restartContext(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * force entire app to be restarted  App is Destroyed ->rebuild
     *
     * @param context  -->launcher context
     * @param genClass -->Landing Activity
     */
    public void restartApp(Context context, Class<?> genClass) {
        Intent mStartActivity = new Intent(context, genClass);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            if(locationProviders == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2 ){
              return true ;
            }
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}
