package com.mindfiresolutions.resourcemanager.utility;

/**
 * This class implements drawer and can be used as utility by using its functions
 * Created by Shivangi Singh on 4/2/2017
 */
//TODO change after discussion
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.admin.AddHardwareActivity;
import com.mindfiresolutions.resourcemanager.admin.AddSoftwareActivity;
import com.mindfiresolutions.resourcemanager.admin.AdminHomeMainActivity;
import com.mindfiresolutions.resourcemanager.commonAdminUser.ChangePasswordActivity;
import com.mindfiresolutions.resourcemanager.commonAdminUser.EditUserProfile;
import com.mindfiresolutions.resourcemanager.launcherPackage.ForgotPasswordActivity;
import com.mindfiresolutions.resourcemanager.launcherPackage.LoginActivity;
import com.mindfiresolutions.resourcemanager.user.userHome.HomeActivity;
import com.mindfiresolutions.resourcemanager.user.userResource.NewRequestActivity;
import com.mindfiresolutions.resourcemanager.user.userResource.UserRequestsActivity;

import static com.mindfiresolutions.resourcemanager.utility.SharedPref.TOKEN;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.USERPREFERENCES;

public class Drawer {
    private static final String TAG = Drawer.class.getSimpleName();
    private SharedPreferences mSharedPreferences;

    public Drawer() {
    }

    public void setDrawerToggle(View drawerView, Toolbar toolbar, Context context, View navView) {

        DrawerLayout drawer = (DrawerLayout) drawerView;
        Activity activity = (Activity) context;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) navView;
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_home_drawer);
        navigationView.getItemBackground();

    }

    public void onBackPressed(DrawerLayout drawerLayout) {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public boolean onNavigationItemSelectedUser(MenuItem item, final Context context, View drawerView) {
        LoggerUtility.log(TAG, "Drawer open functionality");
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_editProfile) {
            Intent i = new Intent(context, EditUserProfile.class);
            context.startActivity(i);
        } else if (id == R.id.nav_logout) {
            handleLogOut(context);
            // Handle the logout action
        } else if (id == R.id.nav_home) {
            Intent i = new Intent(context, HomeActivity.class);
            context.startActivity(i);
            // Handle the remove resource action
        } else if (id == R.id.nav_addResource_hardware) {
            //handle the placeholder resource action
            Intent i = new Intent(context, NewRequestActivity.class);
            context.startActivity(i);
        } else if (id == R.id.nav_my_requests) {
            Intent i = new Intent(context, UserRequestsActivity.class);
            context.startActivity(i);
        } else if (id == R.id.nav_addResource_software) {
            //handle the placeholder resource action
            Intent i = new Intent(context, NewRequestActivity.class);
            context.startActivity(i);
        } else if (id == R.id.nav_chngPassword) {
            Intent i = new Intent(context, ChangePasswordActivity.class);
            context.startActivity(i);
            //TODO validate newPassword and cnfrmPassword
        }
        LoggerUtility.log(TAG, "drawer closed after option is selected");
        DrawerLayout drawer = (DrawerLayout) drawerView;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean onNavigationItemSelectedAdmin(MenuItem item, final Context context, View drawerView) {
        LoggerUtility.log(TAG, "Drawer open functionality");
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_editProfile) {
            Intent i = new Intent(context, EditUserProfile.class);
            context.startActivity(i);
        } else if (id == R.id.nav_logout) {
            handleLogOut(context);

        } else if (id == R.id.nav_home) {
            Intent i = new Intent(context, AdminHomeMainActivity.class);
            context.startActivity(i);
        } else if (id == R.id.nav_addResource_hardware) {
            //handle the placeholder resource action
            Intent i = new Intent(context, AddHardwareActivity.class);
            context.startActivity(i);
        } else if (id == R.id.nav_addResource_software) {
            //handle the placeholder resource action
            Intent i = new Intent(context, AddSoftwareActivity.class);
            context.startActivity(i);
        } else if (id == R.id.nav_chngPassword) {
            Intent i = new Intent(context, ChangePasswordActivity.class);
            context.startActivity(i);
            //TODO validate newPassword and cnfrmPassword
        }
        LoggerUtility.log(TAG, "drawer closed after option is selected");
        DrawerLayout drawer = (DrawerLayout) drawerView;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleLogOut(final Context context) {
        mSharedPreferences = context.getSharedPreferences(USERPREFERENCES, Context.MODE_PRIVATE);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(R.string.cnfrm_logout);
        // set dialog message
        alertDialogBuilder
                .setMessage(R.string.ok_to_continue)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        SharedPref sharedPref= SharedPref.getInstance(context);
                        sharedPref.clearShredPref();
                        Intent i = new Intent(context, LoginActivity.class);
                        ((Activity)context).finish();
                        context.startActivity(i);

                        // Handle the logout action

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

}

//gravity is standard constants and tools for placing an object in large container

