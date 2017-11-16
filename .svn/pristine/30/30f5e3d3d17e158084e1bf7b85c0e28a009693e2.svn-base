package com.mindfiresolutions.resourcemanager.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.AllResourceRequestSummary;
import com.mindfiresolutions.resourcemanager.model.UserRequest;
import com.mindfiresolutions.resourcemanager.model.ViewSoftwareSummary;
import com.mindfiresolutions.resourcemanager.resource.MultiColorListViewAdapter;
import com.mindfiresolutions.resourcemanager.resource.PendingRequestCVAdapter;
import com.mindfiresolutions.resourcemanager.resource.RecyclerItemClickListener;
import com.mindfiresolutions.resourcemanager.resource.RecyclerViewAdapter;
import com.mindfiresolutions.resourcemanager.user.userHome.HomeActivity;
import com.mindfiresolutions.resourcemanager.utility.ApiCallHandler;
import com.mindfiresolutions.resourcemanager.utility.CheckForExpiry;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ADMIN_NAME;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_BY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.DESCRIPTION;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_TYPE_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_BY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_ON;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_TILL;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUEST_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_CATEGORY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_CATEGORY_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_NAME;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_TITLE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_TYPE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.USER_ID;


public class RequestSummaryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        ApiCallHandler.Listener  {

    private static final String TAG = RequestSummaryActivity.class.getSimpleName();
    private ArrayList<HashMap<String, String>> mRequestList;
    private ArrayList<HashMap<String, String>> mRequestSearchList;
    private List<UserRequest> mUserRequests;
    private PendingRequestCVAdapter adapter;
    private boolean isSearchVisible = false;
    private SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_request_summary);
        mSearchView = (SearchView)findViewById(R.id.pd_rqst_search_view);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new PendingRequestCVAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(RequestSummaryActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mRecyclerView.setAdapter(adapter);

        ActionBar ab = getSupportActionBar();
        // check for Actionbar if present then set tittle
        if (ab != null && ab.isShowing()) {
            ab.setTitle(getString(R.string.app_name));
            ab.setSubtitle(getString(R.string.pending_requests));
            ab.setDisplayHomeAsUpEnabled(true);
        }
        initViews();
        fillList();

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(RequestSummaryActivity.this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent i = new Intent(RequestSummaryActivity.this, RequestDetailByIdActivity.class);
                //      i.putExtra(REQUEST_DETAILS, );
//                        if(mIsListSearched)
//                        {

                HashMap<String,String> resource = adapter.getMyViewsListRes(position);
                i.putExtra(RESOURCE_TITLE, resource.get(RESOURCE_TITLE));
                i.putExtra(DESCRIPTION, resource.get(DESCRIPTION));
                i.putExtra(ADMIN_NAME, resource.get(ADMIN_NAME));
                i.putExtra(REQUESTED_ON, resource.get(REQUESTED_ON));
                i.putExtra(REQUESTED_TILL, resource.get(REQUESTED_TILL));
                i.putExtra(REQUESTED_BY, resource.get(REQUESTED_BY));
                i.putExtra(USER_ID, resource.get(USER_ID));
                i.putExtra(REQUEST_ID, resource.get(REQUEST_ID));
                i.putExtra(RESOURCE_CATEGORY, resource.get(RESOURCE_CATEGORY));
                i.putExtra(RESOURCE_NAME, resource.get(RESOURCE_NAME));
                i.putExtra(HARDWARE_TYPE_ID,resource.get(HARDWARE_TYPE_ID));
                i.putExtra(RESOURCE_CATEGORY_ID,resource.get(RESOURCE_CATEGORY_ID));
                i.putExtra(HARDWARE_ID,resource.get(HARDWARE_ID));
                i.putExtra(ASSIGNED_BY,resource.get(ASSIGNED_BY));

                startActivity(i);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                // LoggerUtility.makeShortToast(RequestSummaryActivity.this,mRequestList.get(position).get(RESOURCE_TITLE));
                LoggerUtility.log(TAG, "inside on item selected");
                LoggerUtility.log(TAG, mRequestList.get(position).get(RESOURCE_TITLE));
                LoggerUtility.log(TAG, mRequestList.get(position).get(ADMIN_NAME));
                LoggerUtility.log(TAG, "" + mRequestList.get(position).get(USER_ID));

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    /**
     * Initiate views
     */
    private void initViews() {
        mRequestList = new ArrayList<>();
        mRequestSearchList = new ArrayList<>();
        SearchView mSearchView = (SearchView) findViewById(R.id.pd_rqst_search_view);
        mSearchView.setOnQueryTextListener(this);
    }

    /**
     * Call API to fill list
     */
    private void fillList() {
        final String TOKEN = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<AllResourceRequestSummary> call = apiCallHandler.getInterface().getResourceRequestSummary(LOGIN_REQUEST_HEADER_VALUE, TOKEN);
        apiCallHandler.getResponse(call, null, this, this);
    }

    @Override
    public void onSuccessResult(Object result, String key) {
       try {
            AllResourceRequestSummary response = (AllResourceRequestSummary) result;
            List<UserRequest> userRequest = response.getHardwareRequests();
            userRequest.addAll(response.getSoftwareRequests());
            userRequest.addAll(response.getSharedResourceRequests());

            for (UserRequest request : userRequest) {
                HashMap<String, String> requestListHashMap = new HashMap<>();
                requestListHashMap.put(RESOURCE_TITLE, request.getTitle());
                requestListHashMap.put(RESOURCE_NAME, request.getRequestedDevice());
                requestListHashMap.put(ADMIN_NAME, request.getAssignedTo());
                requestListHashMap.put(REQUESTED_ON, request.getFromDate().substring(0, 10));
                requestListHashMap.put(REQUESTED_TILL, request.getToDate().substring(0, 10));
                requestListHashMap.put(DESCRIPTION, request.getDescription());
                requestListHashMap.put(REQUESTED_BY, request.getRequestedBy());
                requestListHashMap.put(REQUEST_ID, String.valueOf(request.getRequestID()));
                requestListHashMap.put(USER_ID, String.valueOf(request.getUserID()));
                requestListHashMap.put(RESOURCE_CATEGORY, request.getCategory());
                requestListHashMap.put(RESOURCE_CATEGORY_ID, String.valueOf(request.getCategoryID()));
                requestListHashMap.put(HARDWARE_TYPE_ID,String.valueOf(request.getRequestedDeviceID()));
                requestListHashMap.put(HARDWARE_ID,String.valueOf(request.getHardwareID())); requestListHashMap.put(ASSIGNED_BY,String.valueOf(request.getAssignedBy()));
                mRequestList.add(requestListHashMap);
            }
        adapter.setViewitemsList(mRequestList);
        adapter.notifyDataSetChanged();
        }
        catch (NullPointerException npe)
        {
            LoggerUtility.toastLong(RequestSummaryActivity.this,getString(R.string.error_requesting_api));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        isSearchVisible = false;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.action_search:
                if (!isSearchVisible) {
                    mSearchView.setVisibility(View.VISIBLE);
                    isSearchVisible = true;
                } else {
                    mSearchView.setVisibility(View.GONE);
                    isSearchVisible = false;
                }
                break;

            case android.R.id.home:
                this.finish();
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter(newText);
        return true;
    }

    private void filter(String newText) {
        newText = newText.toLowerCase(Locale.getDefault());
        LoggerUtility.log(TAG, "inside filter method");
        mRequestSearchList.clear();

        if (newText.length() == 0) {
            mRequestSearchList.addAll(mRequestList);
        } else {
            for (HashMap<String, String> hardware : mRequestList) {
                String resName = (hardware == null) ? null : hardware.get(RESOURCE_TITLE);
                String assinedTo = (hardware == null) ? null : hardware.get(ADMIN_NAME);
                if ((resName != null && resName.toLowerCase(Locale.getDefault()).contains(newText)) ||
                        (assinedTo != null && assinedTo.toLowerCase(Locale.getDefault()).contains(newText))) {
                    mRequestSearchList.add(hardware);
                }
            }
        }
        adapter.setViewitemsList(mRequestSearchList);
        adapter.notifyDataSetChanged();
    }


}
