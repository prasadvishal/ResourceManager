package com.mindfiresolutions.resourcemanager.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.IdSetter;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterBase;
import com.mindfiresolutions.resourcemanager.model.SoftwareDetails;
import com.mindfiresolutions.resourcemanager.model.SoftwareDetailsResponse;
import com.mindfiresolutions.resourcemanager.model.SoftwareIdSetter;
import com.mindfiresolutions.resourcemanager.utility.ApiCallHandler;
import com.mindfiresolutions.resourcemanager.utility.CheckForExpiry;
import com.mindfiresolutions.resourcemanager.utility.DateUtils;
import com.mindfiresolutions.resourcemanager.utility.InternetConnections;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;

import retrofit2.Call;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.SOFTWARE_COUNT;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.SOFTWARE_DETAILS;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.SOFTWARE_ID;

/**
 * This activity displays software details. it has buttons to edit, delete and assign software
 * created by Shivangi singh on 15th April
 * modified on 30th May
 */

public class SoftwareDetailsActivity extends AppCompatActivity implements View.OnClickListener, ApiCallHandler.Listener {

    private static final String TAG = SoftwareDetailsActivity.class.getSimpleName();
    private int mSoftwareId, mSoftwareCount;
    private SoftwareDetails mSoftwareDetails;
    private static final String KEY_DELETE = "Delete";
    private static final String KEY_FILL_DETAILS = "FillDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software_details);

        ActionBar ab = getSupportActionBar();
        // check for Actionbar if present then set tittle
        if (ab != null && ab.isShowing()) {
            ab.setTitle(getString(R.string.app_name));
            ab.setSubtitle(R.string.software_details);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        Intent i = getIntent();
        mSoftwareId = i.getExtras().getInt(SOFTWARE_ID);
        mSoftwareCount = i.getExtras().getInt(SOFTWARE_COUNT);
        LoggerUtility.log(TAG, "Intent Data received: " + mSoftwareId);
        String softwareCount = Integer.toString(mSoftwareCount);
        TextView txtSoftwareCount = ((TextView) findViewById(R.id.software_details_txt_no_license));
        txtSoftwareCount.setText(softwareCount);

        if (InternetConnections.checkInternetConnection(getApplicationContext())) {
            LoggerUtility.log(TAG, "INTERNET connection available");
            fillSoftwareDetails(mSoftwareId);
            findViewById(R.id.software_detail_btn_delete).setOnClickListener(this);
            findViewById(R.id.software_detail_btn_edit).setOnClickListener(this);

        } else {
            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.no_internet));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.software_detail_btn_delete):
                deleteSoftware(mSoftwareId);
                break;

            case (R.id.software_detail_btn_edit):
                editSoftware();

                break;

            default:
                LoggerUtility.log(TAG, "No Matching cases");
        }
    }

    //to delete the software
    private void deleteSoftware(int softwareID) {
        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<ResponseGetterBase> call = apiCallHandler.getInterface()
                .deleteSoftware(LOGIN_REQUEST_HEADER_VALUE, token, softwareID);
        apiCallHandler.getResponse(call, KEY_DELETE, this, this);
    }

    //to edit the software open AddSoftwareActivity in edit mode
    private void editSoftware() {
        Intent i = new Intent(this, AddSoftwareActivity.class);
        i.putExtra(SOFTWARE_DETAILS, getSoftwareDetails());
        i.putExtra(SOFTWARE_COUNT, mSoftwareCount);
        i.putExtra(SOFTWARE_ID, mSoftwareId);
        if (i.hasExtra(SOFTWARE_DETAILS))
            startActivity(i);
        else
            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.unable_to_connect));
    }

    //get software details so that it may be filled in corresponding fields
    private void fillSoftwareDetails(int softwareID) {
        final String token = CheckForExpiry.checkForToken(this);
        SoftwareIdSetter softwareDetailIdSetter = new SoftwareIdSetter();
        softwareDetailIdSetter.setSoftwareId(mSoftwareId);
        IdSetter idSetter = new IdSetter();
        idSetter.setId(softwareID);
        LoggerUtility.log(TAG, "software id received: " + mSoftwareId);

        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<SoftwareDetailsResponse> call = apiCallHandler.getInterface()
                .getSoftwareDetailById(LOGIN_REQUEST_HEADER_VALUE, token, softwareDetailIdSetter);
        apiCallHandler.getResponse(call, KEY_FILL_DETAILS, this, this);
        LoggerUtility.log(TAG, "response in call object in SoftwareDetailsActivity");
    }

    //set the corresponding fields with the data just received
    private void setFields(SoftwareDetails softwareDetails) {
        TextView txtSoftwareDsc = (TextView) findViewById(R.id.software_details_txt_software_desc);
        if(softwareDetails.getDescription()!=null)
        txtSoftwareDsc.setText(softwareDetails.getDescription());

        TextView txtSoftwareCreatedBy = (TextView) findViewById(R.id.software_details_txt_created_admin_name);
        if(softwareDetails.getCreatedBy()!=null)
        txtSoftwareCreatedBy.setText(softwareDetails.getCreatedBy());

        TextView txtSoftwareCreatedDate = (TextView) findViewById(R.id.software_details_txt_created_date);
        if(softwareDetails.getCreationDate()!=null)
        txtSoftwareCreatedDate.setText(DateUtils.getTrimmedDate(softwareDetails.getCreationDate()));

        TextView txtSoftwareName = (TextView) findViewById(R.id.software_details_txt_software_name);
        if(softwareDetails.getSoftwareName()!=null)
        txtSoftwareName.setText(softwareDetails.getSoftwareName());

        TextView txtSoftwareVersion = (TextView) findViewById(R.id.software_details_txt_product__version_image);
        if(softwareDetails.getVersion()!=null)
        txtSoftwareVersion.setText(softwareDetails.getVersion());

        TextView txtSoftwareLicenseStatus = (TextView) findViewById(R.id.software_details_txt_license_status);
        if(softwareDetails.getLicenseType()!=null)
        txtSoftwareLicenseStatus.setText(String.valueOf(softwareDetails.getLicenseType()));

        TextView txtSoftwareModifiedBy = (TextView) findViewById(R.id.software_details_txt_modified_by);
        if(softwareDetails.getModifiedBy()!=null)
        txtSoftwareModifiedBy.setText(softwareDetails.getModifiedBy());

        TextView txtSoftwareModifiedDate = (TextView) findViewById(R.id.software_details_txt_modified_date);
        if(softwareDetails.getDescription()!=null)
        txtSoftwareModifiedDate.setText(DateUtils.getTrimmedDate(softwareDetails.getModifiedDate()));
    }

    //setter to set software details
    private void setSoftwareDetails(SoftwareDetails softwareDetails) {
        this.mSoftwareDetails = softwareDetails;
    }

    //getter to get software details
    private SoftwareDetails getSoftwareDetails() {
        return mSoftwareDetails;
    }

    //positive callBack method
    @Override
    public void onSuccessResult(Object result, String KEY) {
        switch (KEY) {
            case (KEY_DELETE):
                callBackDelete(result);
                break;
            case (KEY_FILL_DETAILS):
                callBackFillDetails(result);
                break;
            default:
                break;
        }
    }

    //handle positive callBack for delete action
    private void callBackDelete(Object result) {
        ResponseGetterBase responseGetterBase = (ResponseGetterBase) result;
        LoggerUtility.log(TAG, "Response body");
        try {
            LoggerUtility.toastShort(getApplicationContext(), responseGetterBase.getMessage());

        } catch (NullPointerException e) {
            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.error_requesting_api));
        }
    }

    //handle positive callBack to fill details
    private void callBackFillDetails(Object result) {
        SoftwareDetailsResponse softwareDetailsResponse = (SoftwareDetailsResponse) result;
        LoggerUtility.log(TAG, "Response body");
        try {
            if (((SoftwareDetailsResponse) result).getResponse().getCode() == OK) {
                setFields(softwareDetailsResponse.getSoftwareDetails());
                setSoftwareDetails(softwareDetailsResponse.getSoftwareDetails());
            }
            else
            {
                LoggerUtility.toastLong(SoftwareDetailsActivity.this,((SoftwareDetailsResponse) result).getResponse().getMessage());
                startActivity(new Intent(this,SoftwareSummaryActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }

        } catch (NullPointerException e) {
            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.error_requesting_api));
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
}

