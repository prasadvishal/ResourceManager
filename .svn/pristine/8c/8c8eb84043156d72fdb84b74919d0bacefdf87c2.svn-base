package com.mindfiresolutions.resourcemanager.launcherPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.admin.AdminHomeActivity;
import com.mindfiresolutions.resourcemanager.user.userHome.HomeActivity;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;
import com.mindfiresolutions.resourcemanager.utility.SharedPref;

import java.util.Timer;
import java.util.TimerTask;

import static com.mindfiresolutions.resourcemanager.utility.APIconstants.ADMIN;
import static com.mindfiresolutions.resourcemanager.utility.APIconstants.USER;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.ROLE;

/**
 * Created By: Shivangi on: 25/3/2017
 * Last modified on: 23/5/2017
 * this is splash screen to be shown when app launches
 */
public class LauncherActivity extends AppCompatActivity {
    private static final String TAG = LauncherActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        //test if roll is present in sharedPreferences if present it must be one of user or admin
        SharedPref sharedPref = SharedPref.getInstance(this);
        String role = sharedPref.getSharedPreferences(ROLE);
        LoggerUtility.log(TAG, role);
        if (role != null) {
            switch (role) {
                case (USER):
                    startNewActivity(HomeActivity.class);
                    return;

                case (ADMIN):
                    startNewActivity(AdminHomeActivity.class);
                    return;

                default:
                    break;
            }
        }
        //else go with default flow and show login screen
        new Timer().schedule(new TimerTask() {
            public void run() {
                Intent i = new Intent(LauncherActivity.this, LoginActivity.class);
                LauncherActivity.this.finish();
                startActivity(i);
            }
        }, getResources().getInteger(R.integer.splash_timing));
    }

    //start new activity
    private void startNewActivity(Class activity) {
        startActivity(new Intent(this, activity));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }
}
