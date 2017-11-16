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

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.AssignSoftwareSetter;
import com.mindfiresolutions.resourcemanager.model.KeyDetailsResponse;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterBase;
import com.mindfiresolutions.resourcemanager.model.SoftwareIdSetter;
import com.mindfiresolutions.resourcemanager.model.SoftwareKeyList;
import com.mindfiresolutions.resourcemanager.utility.ApiCallHandler;
import com.mindfiresolutions.resourcemanager.utility.CheckForExpiry;
import com.mindfiresolutions.resourcemanager.utility.KeyValueConstants;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;
import com.mindfiresolutions.resourcemanager.utility.SharedPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_ASSIGN_RESOURCE;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_GET_RESOURCE;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_SOFTWARE_KEY;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_BY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_TYPE_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_BY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUEST_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.USER_ID;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.USERID;

/**
 * This class deals with assigning pending software request which comes to admin
 * Created on 6/6/2017 by Shivangi Singh
 */

public class AssignPendingSoftwareActivity extends AppCompatActivity implements ApiCallHandler.Listener,
        AdapterView.OnItemClickListener, AdapterView.OnClickListener {
    private static final String TAG = AssignPendingSoftwareActivity.class.getSimpleName();
    private int mSoftwareId;
    private List<HashMap<String, String>> mAvailableKey;
    private AssignSoftwareSetter mAssignSoftwareSetter;
    private ListView mKeyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_pending_resource);
        mAssignSoftwareSetter = new AssignSoftwareSetter();
        mKeyList = (ListView) findViewById(R.id.assign_resource_lv_listView);
        mKeyList.setOnItemClickListener(this);
        findViewById(R.id.assign_resource_btn_assign).setOnClickListener(this);
        getIntentExtras();
        getDescription();
        getSoftwareKeys();
    }

    /**
     * Method to get intentExtras
     */
    private void getIntentExtras() {
        Intent i = getIntent();
        mSoftwareId = Integer.parseInt(i.getStringExtra(HARDWARE_TYPE_ID));
        mAssignSoftwareSetter.setSoftwareID(mSoftwareId);
        mAssignSoftwareSetter.setHardwareID(Integer.parseInt(i.getStringExtra(HARDWARE_ID)));
        mAssignSoftwareSetter.setRequestID(Integer.parseInt(i.getStringExtra(REQUEST_ID)));
        mAssignSoftwareSetter.setUserID(Integer.parseInt(i.getStringExtra(USER_ID)));
        mAssignSoftwareSetter.setAssignedBy(Integer.parseInt(SharedPref.getInstance(this).getSharedPreferences(USERID)));
    }

    /**
     * method to retrieve text from description, editText field
     */
    private void getDescription() {
        EditText edtDesc = (EditText) findViewById(R.id.assign_resource_edt_description);
        mAssignSoftwareSetter.setDescription(edtDesc.getText().toString());
    }

    /**
     * Method to get list of softwareKeys
     */
    private void getSoftwareKeys() {
        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        SoftwareIdSetter softwareIdSetter = new SoftwareIdSetter();
        softwareIdSetter.setSoftwareId(mSoftwareId);
        Call<KeyDetailsResponse> call = apiCallHandler.getInterface().getSoftwareKey(LOGIN_REQUEST_HEADER_VALUE, token, softwareIdSetter);
        apiCallHandler.getResponse(call, KEY_SOFTWARE_KEY, this, this);
    }

    /**
     * call api to assign the resource
     */
    @Override
    public void onClick(View v) {
        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<ResponseGetterBase> call = apiCallHandler.getInterface().assignSoftwareKey(LOGIN_REQUEST_HEADER_VALUE, token, mAssignSoftwareSetter);
        apiCallHandler.getResponse(call, KEY_ASSIGN_RESOURCE, this, this);
    }

    @Override
    public void onSuccessResult(Object result, String key) {
        switch (key) {
            case (KEY_SOFTWARE_KEY):
                callbackGetKey(result);
                break;

            case (KEY_ASSIGN_RESOURCE):
                callbackAssignResource(result);
                break;

            default:
                break;

        }
    }

    /**
     * method to handle positive callback to get all the keys
     */
    private void callbackGetKey(Object result) {
        mAvailableKey = new ArrayList<>();
        try {
            KeyDetailsResponse response = (KeyDetailsResponse) result;
            List<SoftwareKeyList> softwareKeyLists = response.getSoftwareKeyList();

            if(softwareKeyLists != null)
            for (int i = 0; i < softwareKeyLists.size(); i++) {
                HashMap<String, String> softwareKeyHash = new HashMap<>();
                softwareKeyHash.put(KeyValueConstants.LICENCE_KEY, response.getSoftwareKeyList().get(i).getLicenseKeyValue());
                softwareKeyHash.put(KeyValueConstants.COUNT, String.valueOf(response.getSoftwareKeyList().get(i).getAvailableKeys()));
                softwareKeyHash.put(KeyValueConstants.SOFTWARE_KEY_ID, String.valueOf(response.getSoftwareKeyList().get(i).getSoftwareKeyID()));

                mAvailableKey.add(softwareKeyHash);
            }
            else
            {
                this.finish();
                LoggerUtility.toastShort(this,getString(R.string.no_software_keys_available));
                startActivity(new Intent(this,RequestSummaryActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
            LoggerUtility.log(TAG, "data loaded in hashmap");
            ListAdapter adapter = new SimpleAdapter(AssignPendingSoftwareActivity.this, mAvailableKey, R.layout.list_item_available_hardware_by_type,
                    new String[]{KeyValueConstants.LICENCE_KEY, KeyValueConstants.COUNT}, new int[]{R.id.item_available_hw_txt_resource_name, R.id.item_available_hw_txt_resource_brand});
            mKeyList.setAdapter(adapter);
            LoggerUtility.log(TAG, "after array adapter");
        }
        catch (ClassCastException e)
        {

        }
    }

    /**
     * methods to handle positive callback of assignSoftware
     */
    private void callbackAssignResource(Object result) {
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LoggerUtility.log(TAG, "hardwareID " + mAvailableKey.get(position).get(KeyValueConstants.SOFTWARE_KEY_ID));
        mAssignSoftwareSetter.setSoftwareKey(Integer.parseInt(mAvailableKey.get(position).get(KeyValueConstants.SOFTWARE_KEY_ID)));
    }
}
