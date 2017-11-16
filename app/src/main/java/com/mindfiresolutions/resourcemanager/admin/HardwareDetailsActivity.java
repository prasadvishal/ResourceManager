package com.mindfiresolutions.resourcemanager.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.HardwareById;
import com.mindfiresolutions.resourcemanager.model.HardwareByIdResponse;
import com.mindfiresolutions.resourcemanager.model.HardwareIdSetter;
import com.mindfiresolutions.resourcemanager.model.HardwareImageListResponse;
import com.mindfiresolutions.resourcemanager.model.IdSetter;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterBase;
import com.mindfiresolutions.resourcemanager.utility.ApiCallHandler;
import com.mindfiresolutions.resourcemanager.utility.CheckForExpiry;
import com.mindfiresolutions.resourcemanager.utility.DateUtils;
import com.mindfiresolutions.resourcemanager.utility.InternetConnections;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;
import com.squareup.picasso.Picasso;

import retrofit2.Call;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.BASE_URL_IMAGE;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_DELETE;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_HARDWARE_IMAGE;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_HW_SUMMARY;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_DETAILS;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_ID;

/**
 * Class created to show the full details of the particular hardware which is chosen
 * Created by: Shivangi on 27th March
 * Last Modified on: 5/10/2017
 */

public class HardwareDetailsActivity extends AppCompatActivity implements View.OnClickListener,
        ApiCallHandler.Listener {

    private static final String TAG = HardwareDetailsActivity.class.getSimpleName();
    private HardwareById mHardwareById;
    private static int sHardwareId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware_details);
        ActionBar ab = getSupportActionBar();
        // check for Actionbar if present then set tittle
        if (ab != null && ab.isShowing()) {
            ab.setTitle(getString(R.string.app_name));
            ab.setSubtitle(getString(R.string.hardware_details));
            ab.setDisplayHomeAsUpEnabled(true);
        }

        Intent i = getIntent();
        sHardwareId = i.getExtras().getInt(HARDWARE_ID);
        showHardwareDP();
        //
        if (InternetConnections.hasActiveInternetConnection(getApplicationContext())) {
            LoggerUtility.log(TAG, "INTERNET connection available ");
            getHardwareSummary();
        } else {
            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.no_internet));
        }

        findViewById(R.id.hardware_detail_btn_delete).setOnClickListener(this);
        findViewById(R.id.hardware_detail_btn_edit).setOnClickListener(this);
        findViewById(R.id.hardware_detail_btn_assign).setOnClickListener(this);
    }

    private void showHardwareDP() {
        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        HardwareIdSetter hardwareDetailIdSetter = new HardwareIdSetter();
        hardwareDetailIdSetter.setHardwareId(sHardwareId);
        Call<HardwareImageListResponse> call = apiCallHandler.getInterface().getHardwareImage(LOGIN_REQUEST_HEADER_VALUE,
                token, hardwareDetailIdSetter);
        apiCallHandler.getResponse(call, KEY_HARDWARE_IMAGE, this, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.hardware_detail_btn_delete):
                deleteHardware(sHardwareId);
                break;

            case (R.id.hardware_detail_btn_edit):
                editHardware();
                break;

            case (R.id.hardware_detail_btn_assign):
                assignHardware();
                break;

            default:
                LoggerUtility.log(TAG, "No matching choices");
        }
    }

    //action to handle delete button
    private void deleteHardware(int sHardwareId) {
        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        IdSetter hardwareDetailIdSetter = new IdSetter();
        hardwareDetailIdSetter.setId(sHardwareId);
        Call<ResponseGetterBase> call = apiCallHandler.getInterface().deleteHardware(LOGIN_REQUEST_HEADER_VALUE,
                token, hardwareDetailIdSetter);
        apiCallHandler.getResponse(call, KEY_DELETE, this, this);
    }

    //action to handle edit button
    private void editHardware() {
        Intent i = new Intent(this, AddHardwareActivity.class);
        i.putExtra(HARDWARE_DETAILS, getHardwareById());
        if (i.hasExtra(HARDWARE_DETAILS)) {
            startActivity(i);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } else
            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.unable_to_connect));
    }

    //action to handle assign button
    private void assignHardware() {
        Intent i = new Intent(this, AssignHardwareActivity.class);
        i.putExtra(HARDWARE_ID, sHardwareId);
        startActivity(i);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    //getter to get hardwareById object
    HardwareById getHardwareById() {
        return mHardwareById;
    }

    //setter to set HardwareById
    void setHardwareDetailsByIdGetter(HardwareById hardwareById) {
        this.mHardwareById = hardwareById;
    }

    //call api to get hardware summary
    void getHardwareSummary() {
        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        HardwareIdSetter hardwareDetailIdSetter = new HardwareIdSetter();
        hardwareDetailIdSetter.setHardwareId(sHardwareId);
        Call<HardwareByIdResponse> call = apiCallHandler.getInterface().getHardwareDetailById(LOGIN_REQUEST_HEADER_VALUE,
                token, hardwareDetailIdSetter);
        apiCallHandler.getResponse(call, KEY_HW_SUMMARY, this, this);
    }

    @Override
    public void onSuccessResult(Object result, String KEY) {
        switch (KEY) {
            case (KEY_DELETE):
                callBackDelete(result);
                break;
            case (KEY_HW_SUMMARY):
                callBackHwSummary(result);
                break;
            case (KEY_HARDWARE_IMAGE):
                callbackShowHardwareImage(result);
                break;
            default:
                LoggerUtility.log(TAG, "No choices matched");
        }
    }

    private void callbackShowHardwareImage(Object result) {
        HardwareImageListResponse response = (HardwareImageListResponse) result;
        try {
            ImageView hardwareImg = (ImageView)findViewById(R.id.hardware_details_image);
            if (response.getResponse().getCode() == OK) {

                Picasso.with(this).load(BASE_URL_IMAGE + response.getHardwareImageList().get(0).getImageUrl())
                        .placeholder(R.drawable.placeholder)   // optional
                        .error(R.drawable.no_image)      // optional
                        .fit()
                        .into(hardwareImg, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                //LoggerUtility.toastLong(HardwareDetailsActivity.this, getString(R.string.successful_registeration));
                            }

                            @Override
                            public void onError() {
                                LoggerUtility.toastLong(HardwareDetailsActivity.this, getString(R.string.error_unable_to_fetch_image));
                            }
                        });

            } else {
                LoggerUtility.toastShort(HardwareDetailsActivity.this, response.getResponse().getMessage());
            }
        } catch (NullPointerException e) {
            //LoggerUtility.toastLong(getApplicationContext(), getString(R.string.error_requesting_api));
            //Todo: Handle mismatch response
        }

    }

    //handle positive callBack of delete button and show response to user
    private void callBackDelete(Object result) {
        ResponseGetterBase response = (ResponseGetterBase) result;
        LoggerUtility.log(TAG, "Response body");
        try {
            LoggerUtility.toastShort(getApplicationContext(), response.getMessage());

        } catch (Exception e) {

        }
    }

    //get positive callBacks of hardware details and call funtion to initialize and set set views
    private void callBackHwSummary(Object result) {
        HardwareByIdResponse response = (HardwareByIdResponse) result;
        try {

            if (response.getResponse().getCode() == OK) {
                LoggerUtility.log(TAG, response.getHardwareDetails()[0].toString() + "  " +
                        response.getHardwareDetails()[0].getMACID());
                //send this response object to set values in correponding fields
                setHardwareDetails(response.getHardwareDetails()[0]);
                setHardwareDetailsByIdGetter(response.getHardwareDetails()[0]);
            } else {
                LoggerUtility.toastShort(getApplicationContext(), response.getResponse().getMessage());
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastLong(getApplicationContext(), getString(R.string.error_requesting_api));
        }
    }

    //after getting result from server, set the hardware values in the corresponding fields
    private void setHardwareDetails(HardwareById mHardwareById) {
        TextView macId = (TextView) findViewById(R.id.hardware_details_txt_mac_id);
        macId.setText(mHardwareById.getMACID());

        TextView serviceTag = (TextView) findViewById(R.id.hardware_details_txt_service_tag);
        serviceTag.setText(mHardwareById.getServiceTag());

        TextView orderedDate = (TextView) findViewById(R.id.hardware_details_txt_ordered_date);
        orderedDate.setText(DateUtils.getTrimmedDate(mHardwareById.getOrderDate()));

        TextView recievedDate = (TextView) findViewById(R.id.hardware_details_txt_recieved_date);
        recievedDate.setText(DateUtils.getTrimmedDate(mHardwareById.getReceivedDate()));

        TextView modifiedDate = (TextView) findViewById(R.id.hardware_details_txt_modified_date);
        modifiedDate.setText(mHardwareById.getModifiedDate());

        TextView modifiedBy = (TextView) findViewById(R.id.hardware_details_txt_modified_by);
        modifiedBy.setText(mHardwareById.getModifiedBy());

        TextView status = (TextView) findViewById(R.id.hardware_details_txt_status);
        status.setText(mHardwareById.getStatus());

        TextView sharable = (TextView) findViewById(R.id.hardware_details_txt_is_sharable);
        sharable.setText(mHardwareById.getIsSharable().toString());

        TextView createdBy = (TextView) findViewById(R.id.hardware_details_txt_created_admin_name);
        createdBy.setText(mHardwareById.getCreatedBy());

        TextView creationDate = (TextView) findViewById(R.id.hardware_details_txt_created_date);
        creationDate.setText(DateUtils.getTrimmedDate(mHardwareById.getCreationDate()));

        TextView hardwareModel = (TextView) findViewById(R.id.hrdwr_details_txt_hardware_model);
        hardwareModel.setText((mHardwareById.getModel()));

        TextView hardwareBrand = (TextView) findViewById(R.id.hrdwr_details_txt_hardware_brand);
        hardwareBrand.setText((mHardwareById.getBrand()));

        TextView hardwareType = (TextView) findViewById(R.id.hrdwr_details_txt_hardware_type);
        hardwareType.setText((mHardwareById.getType()));

        TextView hardwareDescription = (TextView) findViewById(R.id.hardware_details_hardware_description);
        hardwareDescription.setText((mHardwareById.getDescription()));
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

