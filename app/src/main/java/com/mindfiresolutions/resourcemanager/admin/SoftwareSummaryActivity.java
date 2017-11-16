package com.mindfiresolutions.resourcemanager.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.SoftwareSummaryWithResponse;
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
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.SOFTWARE_COUNT;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.SOFTWARE_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.SOFTWARE_TYPE;

/**
 * Class to get Software Summary
 * Created by Vishal Prasad on 7/1/2017.
 */

public class SoftwareSummaryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        ApiCallHandler.Listener {

    private static String TAG = com.mindfiresolutions.resourcemanager.admin.HardwareSummaryActivity.class.getSimpleName();
    private ListView mListView;
    private ArrayList<HashMap<String, String>> mSoftwareList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_summary);
        initViews();
        callSoftwareSummary();
        mSoftwareList = new ArrayList<>();
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
            ab.setSubtitle(R.string.software_resource);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Call api to get software summary
     */
    private void callSoftwareSummary() {
        final String TOKEN = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<SoftwareSummaryWithResponse> call = apiCallHandler.getInterface().getSoftwareSummary(LOGIN_REQUEST_HEADER_VALUE, TOKEN);
        apiCallHandler.getResponse(call, null, this, this);
    }

    @Override
    public void onSuccessResult(Object result, String key) {
        SoftwareSummaryWithResponse response = (SoftwareSummaryWithResponse) result;
        try {
            if (response.getResponse().getCode() == OK) {
                LoggerUtility.toastShort(getApplicationContext(), response.getResponse().getMessage());
                List<ViewSoftwareSummary> viewSoftwareSummary;
                viewSoftwareSummary = response.getViewSoftwareSummary();

                for (ViewSoftwareSummary softwareSummary : viewSoftwareSummary) {
                    HashMap<String, String> softwareListHash = new HashMap<>();
                    softwareListHash.put(SOFTWARE_TYPE, softwareSummary.getSoftwareName());
                    softwareListHash.put(SOFTWARE_COUNT, String.valueOf(softwareSummary.getValidCount()));
                    softwareListHash.put(SOFTWARE_ID, String.valueOf(softwareSummary.getSoftwareID()));
                    mSoftwareList.add(softwareListHash);
                }
                ListAdapter adapter = new MultiColorListViewAdapter(this, mSoftwareList, R.layout.list_item_available_hardware_by_type,
                        new String[]{SOFTWARE_TYPE, SOFTWARE_COUNT}, new int[]{R.id.item_available_hw_txt_resource_name, R.id.item_available_hw_txt_resource_brand});
                mListView.setAdapter(adapter);
                LoggerUtility.log(TAG, "after array adapter");
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastLong(getApplicationContext(), getString(R.string.error_requesting_api));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, SoftwareDetailsActivity.class);
        i.putExtra(SOFTWARE_COUNT, Integer.parseInt(mSoftwareList.get(position).get(SOFTWARE_COUNT)));
        i.putExtra(SOFTWARE_ID, Integer.parseInt(mSoftwareList.get(position).get(SOFTWARE_ID)));
        startActivity(i);
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
}


