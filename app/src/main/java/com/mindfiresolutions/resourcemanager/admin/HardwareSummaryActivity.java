package com.mindfiresolutions.resourcemanager.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.HardwareCountResponse;
import com.mindfiresolutions.resourcemanager.model.ViewHardwareSummary;
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
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.COUNT;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_TYPE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_TYPE_ID;

public class HardwareSummaryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        ApiCallHandler.Listener {

    private static String TAG = HardwareSummaryActivity.class.getSimpleName();
    private ListView mListView;
    private ArrayList<HashMap<String, String>> mHardwareList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_summary);
        initViews();
        callHardwareSummary();
        mHardwareList = new ArrayList<>();

    }

    /**
     * Initate views
     */
    private void initViews() {
        mListView = (ListView) findViewById(R.id.admin_resource_list);
        mListView.setOnItemClickListener(this);
        ActionBar ab = getSupportActionBar();
        // check for Actionbar if present then set tittle
        if (ab != null && ab.isShowing()) {
            ab.setTitle(getString(R.string.app_name));
            ab.setSubtitle(R.string.hardware_resource);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Call api to get Hardware Summary
     */
    private void callHardwareSummary() {
        final String TOKEN = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<HardwareCountResponse> call = apiCallHandler.getInterface().getHardwareSummaryCount(LOGIN_REQUEST_HEADER_VALUE, TOKEN);
        apiCallHandler.getResponse(call, null, this, this);
    }

//        CallAPIInterface callAPIInterface = ServiceGenerator.createService(CallAPIInterface.class);
//        LoggerUtility.log(TAG, "Retrofit object created");
//        Call<HardwareCountResponse> call = callAPIInterface.getHardwareSummaryCount(LOGIN_REQUEST_HEADER_VALUE, token);
//        LoggerUtility.log(TAG, "response in call object");
//        call.enqueue(new Callback<HardwareCountResponse>() {
//                         @Override
//                         public void onResponse(Call<HardwareCountResponse> call, Response<HardwareCountResponse> response) {
//                             //response's body
//                             LoggerUtility.log(TAG, "Response body");
//                             progressDialog.dismiss();
//                             try {
//                                 if (response.body().getResponse().getCode() == OK) {
//                                     LoggerUtility.toastShort(getApplicationContext(), response.body().getResponse().getMessage());
//                                     List<ViewHardwareSummary> viewHardwareSummaryList;
//                                     viewHardwareSummaryList = response.body().getHardwareCountSummary();
//                                     for(int i=0;i<viewHardwareSummaryList.size();i++){
//                                         HashMap<String, String> hardwareListHash= new HashMap<>();
//                                         hardwareListHash.put(HARDWARE_TYPE,response.body().getHardwareCountSummary().get(i).getHardwareType());
//                                         hardwareListHash.put(COUNT,String.valueOf(response.body().getHardwareCountSummary().get(i).getCount()));
//                                         hardwareListHash.put(HARDWARE_TYPE_ID, String.valueOf(response.body().getHardwareCountSummary().get(i).getTypeId()));
//                                         mHardwareList.placeholder(hardwareListHash);
//                                     }
//                                     ListAdapter adapter = new MultiColorListViewAdapter(HardwareSummaryActivity.this, mHardwareList, R.layout.list_item_available_hardware_by_type,
//                                             new String[]{HARDWARE_TYPE, COUNT}, new int[]{R.id.item_available_hw_txt_resource_name, R.id.item_available_hw_txt_resource_brand});
//                                     mListView.setAdapter(adapter);
//                                     LoggerUtility.log(TAG, "after array adapter");
//
//
//                                 }
//                             } catch (NullPointerException e) {
//                                 LoggerUtility.toastLong(getApplicationContext(), getString(R.string.error_requesting_api));
//                             }
//                         }
//
//                         @Override
//                         public void onFailure(Call<HardwareCountResponse> call, Throwable t) {
//                             LoggerUtility.log(TAG, "Error requesting API");
//                             progressDialog.dismiss();
//                             LoggerUtility.toastLong(getApplicationContext(), getString(R.string.server_error));
//
//                         }
//                     });
//    }

    @Override
    public void onSuccessResult(Object result, String key) {
        HardwareCountResponse response = (HardwareCountResponse) result;
        try {
            if (response.getResponse().getCode() == OK) {
                LoggerUtility.toastShort(getApplicationContext(), response.getResponse().getMessage());
                List<ViewHardwareSummary> viewHardwareSummaryList;
                viewHardwareSummaryList = response.getHardwareCountSummary();
                for (int i = 0; i < viewHardwareSummaryList.size(); i++) {
                    HashMap<String, String> hardwareListHash = new HashMap<>();
                    hardwareListHash.put(HARDWARE_TYPE, response.getHardwareCountSummary().get(i).getHardwareType());
                    hardwareListHash.put(COUNT, String.valueOf(response.getHardwareCountSummary().get(i).getCount()));
                    hardwareListHash.put(HARDWARE_TYPE_ID, String.valueOf(response.getHardwareCountSummary().get(i).getTypeId()));
                    mHardwareList.add(hardwareListHash);
                }
                ListAdapter adapter = new MultiColorListViewAdapter(HardwareSummaryActivity.this, mHardwareList, R.layout.list_item_available_hardware_by_type,
                        new String[]{HARDWARE_TYPE, COUNT}, new int[]{R.id.item_available_hw_txt_resource_name, R.id.item_available_hw_txt_resource_brand});
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
        Intent i = new Intent(HardwareSummaryActivity.this, HardwareTypeSummary.class);
        i.putExtra(HARDWARE_TYPE_ID, Integer.parseInt(mHardwareList.get(position).get(HARDWARE_TYPE_ID)));
        i.putExtra(HARDWARE_TYPE, mHardwareList.get(position).get(HARDWARE_TYPE));
        startActivity(i);
    }


}
