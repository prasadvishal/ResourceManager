package com.mindfiresolutions.resourcemanager.utility;

import android.app.ProgressDialog;
import android.content.Context;

import com.mindfiresolutions.resourcemanager.R;

/**
 * class created to start progressbar and dismiss it
 * Created by Shivangi Singh on 5/16/2017.
 */
public class ProgressDialogUtility {
    private static ProgressDialog progressDialog;

    //create progress dialog
    public static void startProgressDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(context.getString(R.string.loading));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    /**
     * dismiss progress dialog
     */
    public static void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }
}