package com.mindfiresolutions.resourcemanager.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.mindfiresolutions.resourcemanager.utility.SharedPref.ROLE;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.TOKEN;


/**
 * This class cleans token after specified time.
 * Created by Shivangi Singh on 5/22/2017.
 */

public class AlarmTokenCleanerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LoggerUtility.log("TAG","Removing token related data");
        SharedPref sharedPref= SharedPref.getInstance(context);
        sharedPref.removeToken(TOKEN);
        sharedPref.removeToken(ROLE);
    }
}
