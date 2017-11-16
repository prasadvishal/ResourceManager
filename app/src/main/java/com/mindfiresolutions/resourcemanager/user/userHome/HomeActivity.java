package com.mindfiresolutions.resourcemanager.user.userHome;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.UserIdSetter;
import com.mindfiresolutions.resourcemanager.model.UserMyRessourceResponse;
import com.mindfiresolutions.resourcemanager.model.UserNameSetter;
import com.mindfiresolutions.resourcemanager.model.UserRequest;
import com.mindfiresolutions.resourcemanager.model.UserResponse;
import com.mindfiresolutions.resourcemanager.resource.RecyclerItemClickListener;
import com.mindfiresolutions.resourcemanager.resource.RecyclerViewAdapter;
import com.mindfiresolutions.resourcemanager.user.userResource.NewRequestActivity;
import com.mindfiresolutions.resourcemanager.user.userResource.UserResourceDetailActivity;
import com.mindfiresolutions.resourcemanager.utility.ApiCallHandler;
import com.mindfiresolutions.resourcemanager.utility.CheckForExpiry;
import com.mindfiresolutions.resourcemanager.utility.Drawer;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;
import com.mindfiresolutions.resourcemanager.utility.SharedPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.GET_USER_DETAILS;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_GET_RESOURCE;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_BY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_FROM_DATE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_TO;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_TO_DATE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.DESCRIPTION;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_BY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_ON;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_RESOURCE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_TILL;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUEST_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUEST_STATUS;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_CATEGORY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_NAME;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_TITLE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_TYPE;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.EMAIL;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.IMG_URL;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.NAMEKEY;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.USERID;

//import static com.mindfiresolutions.resourcemanager.utility.UserPreferences.NAMEKEY;
//import static com.mindfiresolutions.resourcemanager.utility.UserPreferences.USERPREFERENCES;

/**
 * Created By: Vishal on: 3/10/2017
 * Last modified on: 3/23/2017
 */

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ApiCallHandler.Listener, View.OnClickListener {

    private final String TAG = HomeActivity.class.getSimpleName();

    private Drawer mDrawer = new Drawer();
    private ArrayList<HashMap<String, String>> mMyRequestsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //created by shivangi
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        //check for Actionbar if present then set tittle
        if (ab != null && ab.isShowing()) {
            ab.setTitle(getString(R.string.application_name));
            ab.setSubtitle("Home");
        }
        LoggerUtility.log(TAG, "Toolbar created");
        
        getUserDetails();

        makeUserMyResourcesApiCall();

        LoggerUtility.log(TAG, "recycler view created ");

        mDrawer.setDrawerToggle(findViewById(R.id.drawer_layout), toolbar, this, findViewById(R.id.nav_view));
        changeNavigationView(findViewById(R.id.nav_view));
        LoggerUtility.log(TAG, "after setDrawerToggle");
        //floating button to placeholder resources
        FloatingActionButton mFabMain = (FloatingActionButton) findViewById(R.id.fab);
        mFabMain.setVisibility(View.VISIBLE);
        mFabMain.setOnClickListener(this);
        LoggerUtility.log(TAG, "Floating action created");
        //drawer toggle

    }

    private void getUserDetails() {
        UserNameSetter userNameSetter = new UserNameSetter();

        userNameSetter.setUserName(SharedPref.getInstance(this).getSharedPreferences(NAMEKEY));

        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<UserResponse> call = apiCallHandler.getInterface()
                .getProfile(LOGIN_REQUEST_HEADER_VALUE, token, userNameSetter);
        apiCallHandler.getResponse(call, GET_USER_DETAILS, this, this);
    }

    private void makeUserMyResourcesApiCall() {
        UserIdSetter userIdSetter = new UserIdSetter();
        userIdSetter.setUserID(Integer.parseInt(SharedPref.getInstance(this).getSharedPreferences(USERID)));

        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<UserMyRessourceResponse> call = apiCallHandler.getInterface()
                .getUserResourcesById(LOGIN_REQUEST_HEADER_VALUE, token, userIdSetter);
        apiCallHandler.getResponse(call, KEY_GET_RESOURCE, this, this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawer.onBackPressed(drawerLayout);
        } else
            super.onBackPressed();

    }

    void changeNavigationView(View view) {
        NavigationView navigationView = (NavigationView) view;
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_home_drawer);
        //change header text to admin
        View admin_view = navigationView.getHeaderView(0);
        TextView admin_txt = (TextView) admin_view.findViewById(R.id.name_user);
        //TODO complete admin name here //done
        //SharedPreferences sharedPreferences = getSharedPreferences(USERPREFERENCES, Context.MODE_PRIVATE);
        admin_txt.setText(SharedPref.getInstance(this).getSharedPreferences(NAMEKEY));
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.fab):
                startActivity(new Intent(HomeActivity.this, NewRequestActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //created by vishal
        LoggerUtility.log(TAG, "inside onNavigationItemSelected");
        return mDrawer.onNavigationItemSelectedUser(item, this, findViewById(R.id.drawer_layout));
    }

    public void onSuccessResult(Object result, String key) {
        switch (key)
        {
            case GET_USER_DETAILS:
                callbackGetUserDetails(result);
                break;
            case KEY_GET_RESOURCE:
                callbackGetUserResources(result);
        }
    }


    private void callbackGetUserDetails(Object result) {
        UserResponse userResponse = (UserResponse) result;
        LoggerUtility.log(TAG, "Response body");

        try {
            SharedPref.getInstance(this).setSharePreferences(IMG_URL,userResponse.getUserProfile().getImageUrl());
            SharedPref.getInstance(this).setSharePreferences(EMAIL,userResponse.getUserProfile().getEmail());

        } catch (NullPointerException e) {
            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.error_requesting_api));

        }
    }

    private void callbackGetUserResources(Object result)
    {UserMyRessourceResponse userMyREsourceREsponse = (UserMyRessourceResponse) result;
        mMyRequestsList = new ArrayList<>();
        LoggerUtility.log(TAG, "Response body");
        //try {
        if (((UserMyRessourceResponse) result).getResponse().getCode() == OK) {
            List<UserRequest> viewUserHardwareResources = userMyREsourceREsponse.getHardwareRequests();
            List<UserRequest> viewUserSoftwareResources = userMyREsourceREsponse.getSoftwareRequests();
            List<UserRequest> viewUserSharedResources = userMyREsourceREsponse.getSharedResourceRequests();

            for (int i = 0; i < viewUserHardwareResources.size(); i++) {
                HashMap<String, String> requestListHashMap = new HashMap<String, String>();
                requestListHashMap.put(RESOURCE_CATEGORY, viewUserHardwareResources.get(i).getCategory());
                requestListHashMap.put(REQUESTED_RESOURCE, viewUserHardwareResources.get(i).getRequestedDevice());
                requestListHashMap.put(ASSIGNED_TO, viewUserHardwareResources.get(i).getAssignedTo());
                requestListHashMap.put(RESOURCE_TITLE, viewUserHardwareResources.get(i).getTitle());
                requestListHashMap.put(ASSIGNED_BY, viewUserHardwareResources.get(i).getAssignedBy());
                requestListHashMap.put(REQUEST_ID, viewUserHardwareResources.get(i).getRequestID() + "");
                requestListHashMap.put(DESCRIPTION, viewUserHardwareResources.get(i).getDescription());
                requestListHashMap.put(RESOURCE_TYPE, viewUserHardwareResources.get(i).getCategoryID() + "");
                requestListHashMap.put(REQUESTED_BY, viewUserHardwareResources.get(i).getRequestedBy());
                requestListHashMap.put(ASSIGNED_FROM_DATE, viewUserHardwareResources.get(i).getFromDate().substring(0, 10));
                requestListHashMap.put(ASSIGNED_TO_DATE, viewUserHardwareResources.get(i).getToDate().substring(0, 10));
                mMyRequestsList.add(requestListHashMap);
            }
            for (int i = 0; i < viewUserSoftwareResources.size(); i++) {
                HashMap<String, String> requestListHashMap = new HashMap<String, String>();
                requestListHashMap.put(RESOURCE_CATEGORY, viewUserSoftwareResources.get(i).getCategory());
                requestListHashMap.put(REQUESTED_RESOURCE, viewUserSoftwareResources.get(i).getRequestedDevice());
                requestListHashMap.put(ASSIGNED_TO, viewUserSoftwareResources.get(i).getAssignedTo());
                requestListHashMap.put(RESOURCE_TITLE, viewUserSoftwareResources.get(i).getTitle());
                requestListHashMap.put(ASSIGNED_BY, viewUserSoftwareResources.get(i).getAssignedBy());
                requestListHashMap.put(RESOURCE_TYPE, viewUserSoftwareResources.get(i).getCategoryID() + "");
                requestListHashMap.put(REQUEST_ID, viewUserSoftwareResources.get(i).getRequestID() + "");
                requestListHashMap.put(DESCRIPTION, viewUserSoftwareResources.get(i).getDescription());
                requestListHashMap.put(REQUESTED_BY, viewUserSoftwareResources.get(i).getRequestedBy());
                requestListHashMap.put(ASSIGNED_FROM_DATE, viewUserSoftwareResources.get(i).getFromDate().substring(0, 10));
                requestListHashMap.put(ASSIGNED_TO_DATE, viewUserSoftwareResources.get(i).getToDate().substring(0, 10));
                mMyRequestsList.add(requestListHashMap);
            }
            for (int i = 0; i < viewUserSharedResources.size(); i++) {
                HashMap<String, String> requestListHashMap = new HashMap<String, String>();
                requestListHashMap.put(RESOURCE_CATEGORY, viewUserSharedResources.get(i).getCategory());
                requestListHashMap.put(REQUESTED_RESOURCE, viewUserSharedResources.get(i).getRequestedDevice());
                requestListHashMap.put(ASSIGNED_TO, viewUserSharedResources.get(i).getAssignedTo());
                requestListHashMap.put(RESOURCE_TITLE, viewUserSharedResources.get(i).getTitle());
                requestListHashMap.put(ASSIGNED_BY, viewUserSharedResources.get(i).getAssignedBy());
                requestListHashMap.put(DESCRIPTION, viewUserSharedResources.get(i).getDescription());
                requestListHashMap.put(RESOURCE_TYPE, viewUserSharedResources.get(i).getCategoryID() + "");
                requestListHashMap.put(REQUEST_ID, viewUserSharedResources.get(i).getRequestID() + "");
                requestListHashMap.put(REQUESTED_BY, viewUserSharedResources.get(i).getRequestedBy());
                requestListHashMap.put(ASSIGNED_FROM_DATE, viewUserSharedResources.get(i).getFromDate().substring(0, 10));
                requestListHashMap.put(ASSIGNED_TO_DATE, viewUserSharedResources.get(i).getToDate().substring(0, 10));
                mMyRequestsList.add(requestListHashMap);
            }
            LoggerUtility.log(TAG, "Response : " + mMyRequestsList.toString());

            RecyclerViewAdapter adapter = new RecyclerViewAdapter(mMyRequestsList);
            RecyclerView myView = (RecyclerView) findViewById(R.id.recycler_view);
            myView.setHasFixedSize(true);
            myView.setAdapter(adapter);
            LinearLayoutManager llm = new LinearLayoutManager(HomeActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            myView.setLayoutManager(llm);

            myView.addOnItemTouchListener(
                    new RecyclerItemClickListener(HomeActivity.this, myView, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent i = new Intent(HomeActivity.this, UserResourceDetailActivity.class);
                            i.putExtra(RESOURCE_NAME, mMyRequestsList.get(position).get(REQUESTED_RESOURCE));
                            i.putExtra(DESCRIPTION, mMyRequestsList.get(position).get(DESCRIPTION));
                            i.putExtra(RESOURCE_CATEGORY, mMyRequestsList.get(position).get(RESOURCE_CATEGORY));
                            i.putExtra(RESOURCE_TITLE, mMyRequestsList.get(position).get(RESOURCE_TITLE));
                            i.putExtra(ASSIGNED_BY, mMyRequestsList.get(position).get(ASSIGNED_BY));
                            i.putExtra(REQUESTED_BY, mMyRequestsList.get(position).get(REQUESTED_BY));
                            i.putExtra(REQUEST_STATUS, mMyRequestsList.get(position).get(REQUEST_STATUS));
                            i.putExtra(REQUESTED_ON, mMyRequestsList.get(position).get(ASSIGNED_FROM_DATE));
                            i.putExtra(REQUESTED_TILL, mMyRequestsList.get(position).get(ASSIGNED_TO_DATE));
                            i.putExtra(REQUEST_ID, Integer.parseInt(mMyRequestsList.get(position).get(REQUEST_ID)));
                            i.putExtra(RESOURCE_TYPE, Integer.parseInt(mMyRequestsList.get(position).get(RESOURCE_TYPE)));
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            // LoggerUtility.makeShortToast(RequestSummaryActivity.this,mMyRequestsList.get(position).get(RESOURCE_NAME));
                            LoggerUtility.log(TAG, "inside on item selected");
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );

        }


//        } catch (NullPointerException e) {
//            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.error_requesting_api));
//       }

    }
}


