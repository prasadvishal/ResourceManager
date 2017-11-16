package com.mindfiresolutions.resourcemanager.admin;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.SharedResourceSummaryWithResponse;
import com.mindfiresolutions.resourcemanager.model.SoftwareSummaryWithResponse;
import com.mindfiresolutions.resourcemanager.model.ViewSharedResource;
import com.mindfiresolutions.resourcemanager.model.ViewSoftwareSummary;
import com.mindfiresolutions.resourcemanager.resource.MultiColorListViewAdapter;
import com.mindfiresolutions.resourcemanager.utility.ApiCallHandler;
import com.mindfiresolutions.resourcemanager.utility.CheckForExpiry;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_TYPE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.SOFTWARE_COUNT;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.SOFTWARE_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.SOFTWARE_TYPE;

public class SharedResourceSummaryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        ApiCallHandler.Listener {

    private static String TAG = com.mindfiresolutions.resourcemanager.admin.HardwareSummaryActivity.class.getSimpleName();
    private ListView mListView;
    private ArrayList<HashMap<String, String>> mSharedResourceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_summary);
        initViews();
        callSharedResourceSummary();
        mSharedResourceList = new ArrayList<>();
    }

    /**
     * Initiate views
     */
    private void initViews() {
        mListView = (ListView) findViewById(R.id.admin_resource_list);
        mListView.setOnItemClickListener(this);

        ActionBar ab = getSupportActionBar();
        // check for Actionbar if present then set tittle
        if (ab != null && ab.isShowing()) {
            ab.setTitle(getString(R.string.app_name));
            ab.setSubtitle(R.string.shared_resources);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Call api to get software summary
     */
    private void callSharedResourceSummary() {
        final String TOKEN = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<SharedResourceSummaryWithResponse> call = apiCallHandler.getInterface().getSharedResourceeSummary(LOGIN_REQUEST_HEADER_VALUE, TOKEN);
        apiCallHandler.getResponse(call, null, this, this);
    }

    @Override
    public void onSuccessResult(Object result, String key) {
        SharedResourceSummaryWithResponse
                response = (SharedResourceSummaryWithResponse) result;
        try {
            if (response.getResponse().getCode() == OK) {
                LoggerUtility.toastShort(getApplicationContext(), response.getResponse().getMessage());
                List<ViewSharedResource> viewSoftwareSummary;
                viewSoftwareSummary = response.getViewSharedResource();

                for (ViewSharedResource softwareSummary : viewSoftwareSummary) {
                    HashMap<String, String> sharedResourceListHash = new HashMap<>();
                    sharedResourceListHash.put(HARDWARE_TYPE, softwareSummary.getHardwareType());
                    sharedResourceListHash.put(SOFTWARE_COUNT, String.valueOf(softwareSummary.getCount()));
                    mSharedResourceList.add(sharedResourceListHash);
                }
                ListAdapter adapter = new MultiColorListViewAdapter(this, mSharedResourceList, R.layout.list_item_available_hardware_by_type,
                        new String[]{HARDWARE_TYPE, SOFTWARE_COUNT}, new int[]{R.id.item_available_hw_txt_resource_name, R.id.item_available_hw_txt_resource_brand});
                mListView.setAdapter(adapter);
                LoggerUtility.log(TAG, "after array adapter");
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastLong(getApplicationContext(), getString(R.string.error_requesting_api));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent i = new Intent(this, SoftwareDetailsActivity.class);
//        i.putExtra(SOFTWARE_COUNT, Integer.parseInt(mSharedResourceList.get(position).get(SOFTWARE_COUNT)));
//        i.putExtra(SOFTWARE_ID, mSharedResourceList.get(position).get(SOFTWARE_ID));
//        startActivity(i);
    }
}


