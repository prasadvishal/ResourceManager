package com.mindfiresolutions.resourcemanager.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.AssignPendingHardwareSetter;
import com.mindfiresolutions.resourcemanager.model.AvailableHardwareByTypeIdResponse;
import com.mindfiresolutions.resourcemanager.model.AvailableHardwareDetails;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterBase;
import com.mindfiresolutions.resourcemanager.model.TypeSetter;
import com.mindfiresolutions.resourcemanager.utility.ApiCallHandler;
import com.mindfiresolutions.resourcemanager.utility.CheckForExpiry;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_GET_RESOURCE;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_SET_RESOURCE;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.BRAND;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_PENDING_REQUEST_OBJECT;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_TYPE_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.MODEL;

/*
* This class deals with assigning pending software request which comes to admin
* Created by shivangi Singh on 5/15/2017
* modified on 5/31/2017
*/

public class AssignPendingHardwareActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnClickListener, ApiCallHandler.Listener {

    private int mResTypeId;
    private static final String TAG = AssignPendingHardwareActivity.class.getSimpleName();
    private static final String HARDWARE_ID = "HardwareID";
    private List<HashMap<String, String>> mAvailableList;
    private ListView mListView;
    private EditText mEdtDescriptionRequest;
    private AssignPendingHardwareSetter mAssignPendingHardwareSetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_pending_resource);

        Intent i = getIntent();
        mAssignPendingHardwareSetter = i.getExtras().getParcelable(HARDWARE_PENDING_REQUEST_OBJECT);
        mResTypeId = Integer.parseInt(i.getStringExtra(HARDWARE_TYPE_ID));

        getResourceList();
        mAvailableList = new ArrayList<>();
        mEdtDescriptionRequest = (EditText) findViewById(R.id.assign_resource_edt_description);
        mListView = (ListView) findViewById(R.id.assign_resource_lv_listView);
        mListView.setOnItemClickListener(this);
        findViewById(R.id.assign_resource_btn_assign).setOnClickListener(this);
        TextView firstListHeader = (TextView)findViewById(R.id.assign_resource_list_header_first);
        TextView secondListHeader = (TextView)findViewById(R.id.assign_resource_list_header_second);
        firstListHeader.setText(getString(R.string.hardware_name));
        secondListHeader.setText(getString(R.string.brand));
    }

    //get a list of hardware resources by type id
    private void getResourceList() {
        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        TypeSetter typeSetter = new TypeSetter();
        typeSetter.setTypeID(mResTypeId);
        Call<AvailableHardwareByTypeIdResponse> call = apiCallHandler.getInterface().getAvailableHardwareByTypeId(LOGIN_REQUEST_HEADER_VALUE, token, typeSetter); // TODO Handle SoftwareCount it after mapping is done
        apiCallHandler.getResponse(call, KEY_GET_RESOURCE, this, this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //view.setBackgroundColor(ContextCompat.getColor(AssignPendingHardwareActivity.this, R.color.themeBlue));
        LoggerUtility.log(TAG, "hardwareID " + mAvailableList.get(position).get(HARDWARE_ID));
        mAssignPendingHardwareSetter.setHardwareID(Integer.parseInt(mAvailableList.get(position).get(HARDWARE_ID)));
        ((TextView)findViewById(R.id.assign_resource_txt_assigning_to)).setText(mAvailableList.get(position).get(MODEL));

    }

    @Override
    public void onClick(View v) {
        mAssignPendingHardwareSetter.setDescription(mEdtDescriptionRequest.getText().toString());
        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<ResponseGetterBase> call = apiCallHandler.getInterface().getAssignRequestedHardware(LOGIN_REQUEST_HEADER_VALUE, token, mAssignPendingHardwareSetter);
        apiCallHandler.getResponse(call, KEY_SET_RESOURCE, this, this);
    }

    @Override
    public void onSuccessResult(Object result, String KEY) {
        switch (KEY) {
            case (KEY_GET_RESOURCE):
                callbackGetResource(result);
                break;
            case (KEY_SET_RESOURCE):
                callbackSetResource(result);
                break;
        }
    }

    //positive callback which fill the available hardwares of the particular type in arraylist
    private void callbackGetResource(Object result) {
        try
        {
        AvailableHardwareByTypeIdResponse response = (AvailableHardwareByTypeIdResponse) result;
        LoggerUtility.log(TAG, "Response body");
        List<AvailableHardwareDetails> availableHardwareDetails;

            if (response.getResponse().getCode() == OK) {
                LoggerUtility.toastShort(AssignPendingHardwareActivity.this, response.getResponse().getMessage());
                availableHardwareDetails = response.getAvailableHardwareDetails();
                if(availableHardwareDetails != null)
                for (int i = 0; i < availableHardwareDetails.size(); i++) {
                    HashMap<String, String> availableResourceHashMap = new HashMap<>();
                    availableResourceHashMap.put(BRAND, response.getAvailableHardwareDetails().get(i).getBrand());
                    LoggerUtility.log(TAG, response.getAvailableHardwareDetails().get(i).getBrand());
                    availableResourceHashMap.put(HARDWARE_ID, String.valueOf(response.getAvailableHardwareDetails().get(i).getHardwareID()));
                    availableResourceHashMap.put(MODEL, response.getAvailableHardwareDetails().get(i).getModel());
                    mAvailableList.add(availableResourceHashMap);
                }
                else
                {
                    this.finish();
                    LoggerUtility.toastShort(this,getString(R.string.no_hardware_available));
                    startActivity(new Intent(this,RequestSummaryActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                LoggerUtility.log(TAG, "data loaded in hashmap");
                ListAdapter adapter = new SimpleAdapter(AssignPendingHardwareActivity.this, mAvailableList, R.layout.list_item_available_hardware_by_type,
                        new String[]{MODEL, BRAND}, new int[]{R.id.item_available_hw_txt_resource_name, R.id.item_available_hw_txt_resource_brand});
                mListView.setAdapter(adapter);
                LoggerUtility.log(TAG, "after array adapter");
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastShort(AssignPendingHardwareActivity.this, getString(R.string.bad_api));

        } catch (java.lang.ClassCastException e) {
            ResponseGetterBase response = (ResponseGetterBase) result;
            LoggerUtility.log(TAG, "Response body");
            if (response.getCode() != OK) {
                LoggerUtility.toastLong(AssignPendingHardwareActivity.this, response.getMessage());

                //TODO ResponseGetterBase response
            }
        }
    }

    //positive callback which gets response once the resource is allocated
    private void callbackSetResource(Object result) {
        ResponseGetterBase response = (ResponseGetterBase) result;
        LoggerUtility.log(TAG, "Response body");
        try {
            if (response.getCode() == OK) {
                LoggerUtility.toastShort(getApplicationContext(), response.getMessage());
                this.finish();
                startActivity(new Intent(this,AdminHomeActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.bad_api));
        }
    }
}
