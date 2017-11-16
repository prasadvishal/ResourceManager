package com.mindfiresolutions.resourcemanager.utility;

import android.widget.EditText;
import android.widget.TextView;

import static com.mindfiresolutions.resourcemanager.utility.LoggerUtility.log;

/**
 * Created by Shivangi Singh on 5/31/2017.
 */

public class GeneralTextUtils {
    public static String getTextFromView(EditText view) {
        return (view.getText().toString().trim());
    }
    public static String getTextFromView(TextView view) {
        return (view.getText().toString().trim());
    }

    public static void requestFocusIfError(EditText view, String errorMsg) {
        view.setError(errorMsg);
        view.requestFocus();
    }

    public static void requestFocusIfError(TextView view, String errorMsg, String tag, String errorLogmsg) {
        view.setError(errorMsg);
        view.requestFocus();
        log(tag, errorLogmsg);
    }
    public static void requestFocusIfError(TextView view, String errorMsg) {
        view.setError(errorMsg);
        view.requestFocus();
    }

    public static void requestFocusIfError(EditText view, String errorMsg, String tag, String errorLogmsg) {
        view.setError(errorMsg);
        view.requestFocus();
        log(tag, errorLogmsg);
    }
}
