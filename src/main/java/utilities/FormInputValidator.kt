package utilities

import android.text.TextUtils
import java.util.regex.Pattern


class FormInputValidator {


    fun isValidEmail(string: String): Boolean {
        val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(string)
        return matcher.matches()
    }

    fun isValidPassword(string: String, allowSpecialChars: Boolean): Boolean {
        val PATTERN: String
        if (allowSpecialChars) {
            //PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
            PATTERN = "^[a-zA-Z@#$%]\\w{5,19}$"
        } else {
            //PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
            PATTERN = "^[a-zA-Z]\\w{5,19}$"
        }


        val pattern = Pattern.compile(PATTERN)
        val matcher = pattern.matcher(string)
        return matcher.matches()
    }

    fun isNullOrEmpty(string: String): Boolean {
        return TextUtils.isEmpty(string)
    }

    fun isNumeric(string: String): Boolean {
        return TextUtils.isDigitsOnly(string)
    }

    //Add more validators here if necessary
}