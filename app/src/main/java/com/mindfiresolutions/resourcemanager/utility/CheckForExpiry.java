package com.mindfiresolutions.resourcemanager.utility;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.launcherPackage.LoginActivity;

import static com.mindfiresolutions.resourcemanager.launcherPackage.LoginActivity.IS_TOKEN_EXPIRE_EXTRA;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.TOKEN;

/**
 * Created by Shivangi Singh on 5/22/2017.
 * This class checks for expiry token and if token is null,
 * redirects to login activity
 */
public class CheckForExpiry {
    public static String checkForToken(Context context) {
        final String token = SharedPref.getInstance(context).getSharedPreferences(TOKEN);
        if (!TextUtils.isEmpty(token)) {
            return token;
        } else {
            LoggerUtility.toastLong(context,context.getString(R.string.token_expired));
            Intent loginIntent = new Intent(context, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            loginIntent.putExtra(IS_TOKEN_EXPIRE_EXTRA, true);
            context.startActivity(loginIntent);
            return null;
        }
    }
}
