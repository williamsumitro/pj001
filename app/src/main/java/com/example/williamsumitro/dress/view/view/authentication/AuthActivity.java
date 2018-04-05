package com.example.williamsumitro.dress.view.view.authentication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WilliamSumitro on 06/04/2018.
 */

public class AuthActivity {
    public static boolean isemailvalid(String email) {
        String _expression = "^[a-z]([a-z0-9-\\.]+)?+@[a-z]+\\.[a-z]{2,4}+(\\.[a-z]{2,4})?$";
        CharSequence _email = email;
        Pattern _pattern = Pattern.compile(_expression, Pattern.CASE_INSENSITIVE);
        Matcher _mathcer = _pattern.matcher(_email);

        if (_mathcer.matches()) {
            return true;
        }
        return false;
    }

    public static boolean ispasswordvalid(String password) {
        String _expression = "^([a-zA-Z0-9@*#]{8,15})$";
        //Password matching expression. Match all alphanumeric character and predefined wild characters.
        // Password must consists of at least 8 characters and not more than 15 characters.
        CharSequence _password = password;
        Pattern _pattern = Pattern.compile(_expression, Pattern.CASE_INSENSITIVE);
        Matcher _mathcer = _pattern.matcher(_password);

        if (_mathcer.matches()) {
            return true;
        }
        return false;
    }
}
