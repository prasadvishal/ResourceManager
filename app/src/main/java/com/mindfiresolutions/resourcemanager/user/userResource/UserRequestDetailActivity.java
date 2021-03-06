package com.mindfiresolutions.resourcemanager.user.userResource;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.DeleteUserRequestSetter;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterBase;
import com.mindfiresolutions.resourcemanager.user.userHome.HomeActivity;
import com.mindfiresolutions.resourcemanager.utility.CallAPIInterface;
import com.mindfiresolutions.resourcemanager.utility.CheckForExpiry;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;
import com.mindfiresolutions.resourcemanager.utility.ServiceGenerator;
import com.mindfiresolutions.resourcemanager.utility.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_TO;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.DESCRIPTION;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_BY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_ON;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_TILL;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUEST_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUEST_STATUS;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_CATEGORY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_NAME;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_TITLE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_TYPE;
//import static com.mindfiresolutions.resourcemanager.utility.UserPreferences.TOKEN;

/**
 * This activity displays Detailed Request made by User
 * created by Shivangi singh on 26th April
 * modified on 30th May
 */

public class UserRequestDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mResourceCategory, mResourceTitle, mRequestedDevice,mRequestedTo,mRequestedBy,mRequestStatus,mRequestDate, mIssueDate, mDescription;
    private static final String TAG = UserRequestDetailActivity.class.getSimpleName();
    private int mRequestId, mCategoryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request_detail);
        initViews();
        fillData();

    }

    private void fillData() {
        Intent i = getIntent();
        mRequestedDevice.setText( i.getExtras().getString(RESOURCE_NAME));
        mResourceCategory.setText( i.getExtras().getString(RESOURCE_CATEGORY));
        mResourceTitle.setText( i.getExtras().getString(RESOURCE_TITLE));
        mRequestedTo.setText( i.getExtras().getString(ASSIGNED_TO));
        mRequestedBy.setText( i.getExtras().getString(REQUESTED_BY));
        mRequestStatus.setText( i.getExtras().getString(REQUEST_STATUS));
        mRequestDate.setText( i.getExtras().getString(REQUESTED_ON));
        mIssueDate.setText( i.getExtras().getString(REQUESTED_TILL));
        mDescription.setText( i.getExtras().getString(DESCRIPTION));
        LoggerUtility.log(TAG,"Request ID: "+i.getExtras().getInt(REQUEST_ID));
        mRequestId = i.getExtras().getInt(REQUEST_ID);
        mCategoryId = i.getExtras().getInt(RESOURCE_TYPE);
    }


    private void initViews() {
        mResourceCategory = (TextView)findViewById(R.id.user_req_res_category);
        mResourceTitle = (TextView)findViewById(R.id.user_req_res_title);
        mRequestedDevice = (TextView)findViewById(R.id.user_req_requested_dev);
        mRequestedTo = (TextView)findViewById(R.id.user_req_assigned_to);
        mRequestedBy = (TextView)findViewById(R.id.user_req_requested_by);
        mRequestStatus = (TextView)findViewById(R.id.user_req_request_status);
        mRequestDate = (TextView)findViewById(R.id.user_req_request_date);
        mIssueDate = (TextView)findViewById(R.id.user_req_request_till);
        mDescription = (TextView)findViewById(R.id.user_req_request_desc);
        findViewById(R.id.user_req_delete_button).setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==(R.id.user_req_delete_button))
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserRequestDetailActivity.this);
            alertDialogBuilder.setTitle(R.string.cnfrm_deletion); //TODO REmove Hardcoded strings //DONE
            // set dialog message
            alertDialogBuilder
                    .setMessage(R.string.promt_delete_confirmation)
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            makeDeleteRequestAPICall();

                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }

    }

    private void makeDeleteRequestAPICall() {

        final String token = CheckForExpiry.checkForToken(this);
        final ProgressDialog progressDialog = new ProgressDialog(UserRequestDetailActivity.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        DeleteUserRequestSetter deleteUserRequestSetter = new DeleteUserRequestSetter();
        deleteUserRequestSetter.setRequestID(mRequestId);
        deleteUserRequestSetter.setResourceType(mCategoryId);
        CallAPIInterface callAPIInterface = ServiceGenerator.createService(CallAPIInterface.class);
        LoggerUtility.log(TAG, "Retrofit object created in updateUserProfile");
        Call<ResponseGetterBase> call = callAPIInterface.deleteMyRequest(LOGIN_REQUEST_HEADER_VALUE, token, deleteUserRequestSetter); // TODO Handle SoftwareCount it after mapping is done
        LoggerUtility.log(TAG, "response in call object in UpdateUserProfile");

        call.enqueue(new Callback<ResponseGetterBase>() {
                         @Override
                         public void onResponse(Call<ResponseGetterBase> call, Response<ResponseGetterBase> response) {
                             //response's body
                             progressDialog.dismiss();
                             LoggerUtility.log(TAG, "Response body");
                             try {
                                 if(response.body().getCode()== OK) {
                                     LoggerUtility.toastShort(getApplicationContext(), response.body().getMessage());
                                     Intent i = new Intent(UserRequestDetailActivity.this, HomeActivity.class);
                                     UserRequestDetailActivity.this.finish();
                                     startActivity(i);
                                     overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                 }
                                 else
                                     LoggerUtility.toastShort(getApplicationContext(), response.body().getMessage());
                             } catch (NullPointerException e) {
                                 LoggerUtility.toastShort(getApplicationContext(), getString(R.string.error_requesting_api));
                             }
                         }

                         @Override
                         public void onFailure(Call<ResponseGetterBase> call, Throwable t) {
                             progressDialog.dismiss();
                             LoggerUtility.log(TAG, "Error requesting API");
                         }
                     }
        );

    }
}
