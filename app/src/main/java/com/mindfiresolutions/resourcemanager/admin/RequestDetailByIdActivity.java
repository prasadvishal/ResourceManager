package com.mindfiresolutions.resourcemanager.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.AssignPendingHardwareSetter;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;
import com.mindfiresolutions.resourcemanager.utility.SharedPref;

import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ADMIN_NAME;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.ASSIGNED_BY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.DESCRIPTION;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_PENDING_REQUEST_OBJECT;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_TYPE_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_BY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_ON;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_RESOURCE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUESTED_TILL;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.REQUEST_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_CATEGORY;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_CATEGORY_ID;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_NAME;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.RESOURCE_TITLE;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.USER_ID;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.USERID;

/**
 * This activity displays details of a particular pending request it is when selected
 * created by Shivangi singh on 5/5/2017
 * modified on 5/30/2017
 */

public class RequestDetailByIdActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int DEFAULT_ID = 0;
    private Intent mIntent;
    private final static String TAG = RequestDetailByIdActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_request_detail_id);

        ActionBar ab = getSupportActionBar();
        // check for Actionbar if present then set tittle
        if (ab != null && ab.isShowing()) {
            ab.setTitle(getString(R.string.app_name));
            ab.setSubtitle(getString(R.string.pending_requests));
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mIntent = getIntent();
        setViews();

        findViewById(R.id.pending_request_btn_approve).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.pending_request_btn_approve):
                assignResource();
        }
    }

    //make a pendingRequestResponseSetter object and set fields accordingly
    private void assignResource() {
        AssignPendingHardwareSetter pendingHardwareSetter = new AssignPendingHardwareSetter();
        pendingHardwareSetter.setRequestID(Integer.parseInt(mIntent.getExtras().getString(REQUEST_ID)));
        pendingHardwareSetter.setUserID(Integer.parseInt(mIntent.getExtras().getString(USER_ID)));
        String adminId = SharedPref.getInstance(this).getSharedPreferences(USERID);
        pendingHardwareSetter.setAssignedBy(Integer.parseInt(adminId));
        pendingHardwareSetter.setHardwareID(DEFAULT_ID);
        pendingHardwareSetter.setDescription("");
        Intent i;
        switch (Integer.parseInt(mIntent.getStringExtra(RESOURCE_CATEGORY_ID)))
        {
            case 1:
                 i = new Intent(this, AssignPendingHardwareActivity.class);
                i.putExtra(HARDWARE_PENDING_REQUEST_OBJECT, pendingHardwareSetter);
                i.putExtra(HARDWARE_TYPE_ID, mIntent.getStringExtra(HARDWARE_TYPE_ID));
                startActivity(i);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;

            case 2:
                i = new Intent(this, AssignPendingSoftwareActivity.class);
                i.putExtra(HARDWARE_PENDING_REQUEST_OBJECT, pendingHardwareSetter);
                i.putExtra(HARDWARE_TYPE_ID, mIntent.getStringExtra(HARDWARE_TYPE_ID));
                i.putExtra(REQUEST_ID,mIntent.getStringExtra(REQUEST_ID));
                i.putExtra(USER_ID,mIntent.getStringExtra(USER_ID));
                i.putExtra(HARDWARE_ID,mIntent.getStringExtra(HARDWARE_ID));


                startActivity(i);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;

            case 3:
                i = new Intent(this, AssignPendingHardwareActivity.class);
                i.putExtra(HARDWARE_PENDING_REQUEST_OBJECT, pendingHardwareSetter);
                i.putExtra(HARDWARE_TYPE_ID, mIntent.getStringExtra(HARDWARE_TYPE_ID));
                startActivity(i);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;

            default:
                LoggerUtility.log(TAG,"Invalid Resource Category Received");

        }

    }

    //set the corresponding views and also initialize views before
    private void setViews() {
        TextView txtRequestedBy = (TextView) findViewById(R.id.pending_details_txt_requested_by);
        txtRequestedBy.setText(mIntent.getExtras().getString(REQUESTED_BY));

        TextView txtRequestedDate = (TextView) findViewById(R.id.pending_details_txt_request_date);
        txtRequestedDate.setText(mIntent.getExtras().getString(REQUESTED_ON));

        TextView txtTitle = (TextView) findViewById(R.id.pending_request_detail_txt_request_title);
        txtTitle.setText(mIntent.getStringExtra(RESOURCE_TITLE));

        TextView txtDescriptionResponse = (TextView) findViewById(R.id.pending_request_detail_txt_request_description);
        txtDescriptionResponse.setText(mIntent.getExtras().getString(DESCRIPTION));

        TextView txtRequestedTo = (TextView) findViewById(R.id.pending_details_txt_requested_to);
        txtRequestedTo.setText(mIntent.getExtras().getString(ADMIN_NAME));

        TextView txtIssueBy = (TextView) findViewById(R.id.pending_details_txt_issue_by);
        txtIssueBy.setText(mIntent.getExtras().getString(REQUESTED_TILL));

        TextView txtResourceType = (TextView) findViewById(R.id.pending_details_txt_resource_type);
        txtResourceType.setText(mIntent.getExtras().getString(RESOURCE_NAME));

        TextView txtResourceCategory = (TextView) findViewById(R.id.pending_details_txt_res_category);
        txtResourceCategory.setText(mIntent.getExtras().getString(RESOURCE_CATEGORY));


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
