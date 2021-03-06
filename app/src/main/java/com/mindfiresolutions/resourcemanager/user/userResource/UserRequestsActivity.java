package com.mindfiresolutions.resourcemanager.user.userResource;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.UserHardwareRequest;
import com.mindfiresolutions.resourcemanager.model.UserIdSetter;
import com.mindfiresolutions.resourcemanager.model.UserMyRequestsResponse;
import com.mindfiresolutions.resourcemanager.model.UserSharedRequest;
import com.mindfiresolutions.resourcemanager.model.UserSoftwareRequest;
import com.mindfiresolutions.resourcemanager.resource.RecyclerItemClickListener;
import com.mindfiresolutions.resourcemanager.resource.RecyclerViewAdapter;
import com.mindfiresolutions.resourcemanager.utility.CallAPIInterface;
import com.mindfiresolutions.resourcemanager.utility.CheckForExpiry;
import com.mindfiresolutions.resourcemanager.utility.Drawer;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;
import com.mindfiresolutions.resourcemanager.utility.ServiceGenerator;
import com.mindfiresolutions.resourcemanager.utility.SharedPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;
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
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.NAMEKEY;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.USERID;

/**
 * This activity displays All requests made by the User in Card view
 * created by Vishal on 26th April
 * modified on 30th May
 */

public class UserRequestsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemClickListener, View.OnClickListener {
    private Drawer mDrawer = new Drawer();
    private static final String TAG = UserRequestsActivity.class.getSimpleName();
    private ListView mListView;
    private List<UserHardwareRequest> viewUserHardwareRequests;
    private List<UserSoftwareRequest> viewUserSoftwareRequests;
    private List<UserSharedRequest> viewUserSharedRequests;
    private ArrayList<HashMap<String, String>> mMyRequestsList;
    private ViewPager viewPager;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        makeDrawer();
        initViews();
        makeMyAllRequestsApiCAll();
   }

    public List<UserHardwareRequest> getViewUserHardwareRequests() {
        return viewUserHardwareRequests;
    }

    public List<UserSoftwareRequest> getViewUserSoftwareRequests() {
        return viewUserSoftwareRequests;
    }

    public List<UserSharedRequest> getViewUserSharedRequests() {
        return viewUserSharedRequests;
    }

    private void makeMyAllRequestsApiCAll() {

        final String token = CheckForExpiry.checkForToken(this);
        //created by Shivangi
        final ProgressDialog progressDialog = new ProgressDialog(UserRequestsActivity.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        UserIdSetter userIdSetter = new UserIdSetter();
        userIdSetter.setUserID(Integer.parseInt(SharedPref.getInstance(this).getSharedPreferences(USERID)));
        CallAPIInterface callAPIInterface = ServiceGenerator.createService(CallAPIInterface.class);
        LoggerUtility.log(TAG, "Retrofit object created");
        Call<UserMyRequestsResponse> call = callAPIInterface.getUserRequestsById(LOGIN_REQUEST_HEADER_VALUE, token, userIdSetter);
        LoggerUtility.log(TAG, token);
        LoggerUtility.log(TAG, "response in call object");
        call.enqueue(new Callback<UserMyRequestsResponse>() {
                         @Override
                         public void onResponse(Call<UserMyRequestsResponse> call, Response<UserMyRequestsResponse> response) {
                             //response's body
                             LoggerUtility.log(TAG, "Response body");
//                             try {
                             progressDialog.dismiss();
                             if (response.body().getResponse().getCode() == OK) {
                                 LoggerUtility.log(TAG, response.body().getResponse().getMessage());
                                 viewUserHardwareRequests = response.body().getUserHardwareRequests();
                                 viewUserSoftwareRequests = response.body().getUserSoftwareRequests();
                                 viewUserSharedRequests = response.body().getUserSharedRequests();
                                 //TODO make custom array adapter
                                 //fill the list with the data recieved from server
                                 //extract just the getHardwareType from the whole object
                                 for (int i = 0; i < viewUserHardwareRequests.size(); i++) {
                                     HashMap<String, String> requestListHashMap = new HashMap<String, String>();
                                     requestListHashMap.put(RESOURCE_CATEGORY, viewUserHardwareRequests.get(i).getCategory());
                                     requestListHashMap.put(REQUESTED_RESOURCE, viewUserHardwareRequests.get(i).getRequestedDevice());
                                     requestListHashMap.put(ASSIGNED_TO, viewUserHardwareRequests.get(i).getAssignedTo());
                                     requestListHashMap.put(RESOURCE_TITLE, viewUserHardwareRequests.get(i).getTitle());
                                     requestListHashMap.put(REQUEST_STATUS, viewUserHardwareRequests.get(i).getRequestStatus());
                                     requestListHashMap.put(REQUEST_ID, viewUserHardwareRequests.get(i).getRequestID().toString());
                                     requestListHashMap.put(DESCRIPTION, viewUserHardwareRequests.get(i).getDescription());
                                     requestListHashMap.put(RESOURCE_TYPE, viewUserHardwareRequests.get(i).getCategoryID().toString());
                                     requestListHashMap.put(REQUESTED_BY, viewUserHardwareRequests.get(i).getRequestedBy());
                                     requestListHashMap.put(ASSIGNED_FROM_DATE, viewUserHardwareRequests.get(i).getFromDate().substring(0, 10));
                                     requestListHashMap.put(ASSIGNED_TO_DATE, viewUserHardwareRequests.get(i).getToDate().substring(0, 10));
                                     mMyRequestsList.add(requestListHashMap);
                                 }
                                 for (int i = 0; i < viewUserSoftwareRequests.size(); i++) {
                                     HashMap<String, String> requestListHashMap = new HashMap<String, String>();
                                     requestListHashMap.put(RESOURCE_CATEGORY, viewUserSoftwareRequests.get(i).getCategory());
                                     requestListHashMap.put(REQUESTED_RESOURCE, viewUserSoftwareRequests.get(i).getRequestedDevice());
                                     requestListHashMap.put(ASSIGNED_TO, viewUserSoftwareRequests.get(i).getAssignedTo());
                                     requestListHashMap.put(RESOURCE_TITLE, viewUserSoftwareRequests.get(i).getTitle());
                                     requestListHashMap.put(REQUEST_STATUS, viewUserSoftwareRequests.get(i).getRequestStatus());
                                     requestListHashMap.put(RESOURCE_TYPE, viewUserSoftwareRequests.get(i).getCategoryID().toString());
                                     requestListHashMap.put(REQUEST_ID, viewUserSoftwareRequests.get(i).getRequestID().toString());
                                     requestListHashMap.put(DESCRIPTION, viewUserSoftwareRequests.get(i).getDescription());
                                     requestListHashMap.put(REQUESTED_BY, viewUserSoftwareRequests.get(i).getRequestedBy());
                                     requestListHashMap.put(ASSIGNED_FROM_DATE, viewUserSoftwareRequests.get(i).getFromDate().substring(0, 10));
                                     requestListHashMap.put(ASSIGNED_TO_DATE, viewUserSoftwareRequests.get(i).getToDate().substring(0, 10));
                                     mMyRequestsList.add(requestListHashMap);
                                 }
                                 for (int i = 0; i < viewUserSharedRequests.size(); i++) {
                                     HashMap<String, String> requestListHashMap = new HashMap<String, String>();
                                     requestListHashMap.put(RESOURCE_CATEGORY, viewUserSharedRequests.get(i).getCategory());
                                     requestListHashMap.put(REQUESTED_RESOURCE, viewUserSharedRequests.get(i).getRequestedDevice());
                                     requestListHashMap.put(ASSIGNED_TO, viewUserSharedRequests.get(i).getAssignedTo());
                                     requestListHashMap.put(RESOURCE_TITLE, viewUserSharedRequests.get(i).getTitle());
                                     requestListHashMap.put(REQUEST_STATUS, viewUserSharedRequests.get(i).getRequestStatus());
                                     requestListHashMap.put(DESCRIPTION, viewUserSharedRequests.get(i).getDescription());
                                     requestListHashMap.put(RESOURCE_TYPE, viewUserSharedRequests.get(i).getCategoryID().toString());
                                     requestListHashMap.put(REQUEST_ID, viewUserSharedRequests.get(i).getRequestID().toString());
                                     requestListHashMap.put(REQUESTED_BY, viewUserSharedRequests.get(i).getRequestedBy());
                                     requestListHashMap.put(ASSIGNED_FROM_DATE, viewUserSharedRequests.get(i).getFromDate().substring(0, 10));
                                     requestListHashMap.put(ASSIGNED_TO_DATE, viewUserSharedRequests.get(i).getToDate().substring(0, 10));
                                     mMyRequestsList.add(requestListHashMap);
                                 }

                                 RecyclerViewAdapter adapter = new RecyclerViewAdapter(mMyRequestsList);
                                 RecyclerView myView =  (RecyclerView)findViewById(R.id.recyclerview);
                                 myView.setHasFixedSize(true);
                                 myView.setAdapter(adapter);
                                 LinearLayoutManager llm = new LinearLayoutManager(UserRequestsActivity.this);
                                 llm.setOrientation(LinearLayoutManager.VERTICAL);
                                 myView.setLayoutManager(llm);

                                 myView.addOnItemTouchListener(
                                         new RecyclerItemClickListener(UserRequestsActivity.this, myView ,new RecyclerItemClickListener.OnItemClickListener() {
                                             @Override public void onItemClick(View view, int position) {
                                                 Intent i = new Intent(UserRequestsActivity.this, UserRequestDetailActivity.class);
                                                 i.putExtra(RESOURCE_NAME, mMyRequestsList.get(position).get(REQUESTED_RESOURCE));
                                                 i.putExtra(DESCRIPTION, mMyRequestsList.get(position).get(DESCRIPTION));
                                                 i.putExtra(RESOURCE_CATEGORY, mMyRequestsList.get(position).get(RESOURCE_CATEGORY));
                                                 i.putExtra(RESOURCE_TITLE, mMyRequestsList.get(position).get(RESOURCE_TITLE));
                                                 i.putExtra(ASSIGNED_TO, mMyRequestsList.get(position).get(ASSIGNED_TO));
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

                                             @Override public void onLongItemClick(View view, int position) {
                                                 // do whatever
                                             }
                                         })
                                 );


                                 //TODO string
                             } else {
                                 LoggerUtility.toastShort(getApplicationContext(), response.body().getResponse().getMessage());
                             }
//                             } catch (Exception e) {
//                                 LoggerUtility.toastLong(getApplicationContext(), getString(R.string.error_requesting_api));
//                             }
                         }


                         @Override
                         public void onFailure(Call<UserMyRequestsResponse> call, Throwable t) {
                             progressDialog.dismiss();
                             LoggerUtility.log(TAG, "Error requesting API");
                         }
                     }
        );

    }

    private void initViews() {
        //mListView = (ListView) findViewById(R.id.user_my_request_list_view);
       // mListView.setOnItemClickListener(this);
        FloatingActionButton mFabMain = (FloatingActionButton) findViewById(R.id.fab);
        mFabMain.setVisibility(View.VISIBLE);
        mFabMain.setOnClickListener(this);
        mMyRequestsList = new ArrayList<>();
    }


    private void makeDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame_activity_layout);
        //set the layout inside coordinator layout's frameLayout
        getLayoutInflater().inflate(R.layout.cardview_layout, frameLayout); //activity_user_requests
        LoggerUtility.log(TAG, "Toolbar created");
        mDrawer.setDrawerToggle(findViewById(R.id.drawer_layout), toolbar, this, findViewById(R.id.nav_view));
        LoggerUtility.log(TAG, "after setDrawerToggle");
        changeNavigationView(findViewById(R.id.nav_view));
        toolbar.setTitle(getString(R.string.resource)+" "+getString(R.string.manager));                    //TODO Remove hard coding
        toolbar.setSubtitle(getString(R.string.propmt_my_requests));
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
        //TODO complete admin name here
        admin_txt.setText(SharedPref.getInstance(this).getSharedPreferences(NAMEKEY)); //Done
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return mDrawer.onNavigationItemSelectedUser(item, this, findViewById(R.id.drawer_layout));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, UserRequestDetailActivity.class);
        i.putExtra(RESOURCE_NAME, mMyRequestsList.get(position).get(REQUESTED_RESOURCE));
        i.putExtra(DESCRIPTION, mMyRequestsList.get(position).get(DESCRIPTION));
        i.putExtra(RESOURCE_CATEGORY, mMyRequestsList.get(position).get(RESOURCE_CATEGORY));
        i.putExtra(RESOURCE_TITLE, mMyRequestsList.get(position).get(RESOURCE_TITLE));
        i.putExtra(ASSIGNED_TO, mMyRequestsList.get(position).get(ASSIGNED_TO));
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
    public void onClick(View v) {
        Context context = UserRequestsActivity.this;
        switch (v.getId()) {
            case (R.id.fab):
                Intent i = new Intent(context, NewRequestActivity.class);
                context.startActivity(i);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }
}
