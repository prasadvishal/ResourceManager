package com.mindfiresolutions.resourcemanager.user.userResource;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.AdminListResponse;
import com.mindfiresolutions.resourcemanager.model.AdminListSetter;
import com.mindfiresolutions.resourcemanager.model.AssignedHardware;
import com.mindfiresolutions.resourcemanager.model.HardwareListForUserResponse;
import com.mindfiresolutions.resourcemanager.model.HardwareTypeResponse;
import com.mindfiresolutions.resourcemanager.model.HardwareTypeSetter;
import com.mindfiresolutions.resourcemanager.model.IdSetter;
import com.mindfiresolutions.resourcemanager.model.NewUserRequestSetter;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterBase;
import com.mindfiresolutions.resourcemanager.model.SoftwareDetails;
import com.mindfiresolutions.resourcemanager.model.SoftwareDetailsSetter;
import com.mindfiresolutions.resourcemanager.model.UserIdSetter;
import com.mindfiresolutions.resourcemanager.resource.CustomSpinnerAdapter;
import com.mindfiresolutions.resourcemanager.resource.RowItem;
import com.mindfiresolutions.resourcemanager.user.userHome.HomeActivity;
import com.mindfiresolutions.resourcemanager.utility.ApiCallHandler;
import com.mindfiresolutions.resourcemanager.utility.CheckForExpiry;
import com.mindfiresolutions.resourcemanager.utility.Drawer;
import com.mindfiresolutions.resourcemanager.utility.GeneralTextUtils;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;
import com.mindfiresolutions.resourcemanager.utility.SharedPref;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.USERID;

/**
 * This activity Makes a new request by user
 * Created By: Vishal on: 3/16/2017
 * Last modified on: 3/23/2017
 */
public class NewRequestActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener, ApiCallHandler.Listener {

    private static final String TAG = NewRequestActivity.class.getSimpleName();
    private final Drawer DRAWER = new Drawer();
    private TextView mTxtIssueDate;
    private SimpleDateFormat mDateFormatter;
    private DatePickerDialog mDatePickerDialog;
    private EditText mEdtTittle;
    private EditText mEdtReqestDescription;
    private Spinner mSpnrResourceCategory, mSpnrSubCategory, mSpnrSelectAdmin, mSpnrSelectHardware;
    private List<SoftwareDetails> mSoftwareDetailsList;
    private List<HardwareTypeSetter> mHardwareTypeList;
    private List<AssignedHardware> mAssignedHardwareList;
    private List<AdminListSetter> mAdminList;
    private List<RowItem> mRowItemsList;
    private SharedPref mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDateFormatter = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());
        mSharedPref = SharedPref.getInstance(this);
        setContentView(R.layout.activity_admin_main);
        makeDrawer();
        initViews();
        //  mSharedPreferences = getSharedPreferences(SharedPref.USERPREFERENCES, Context.MODE_PRIVATE); //Todo Utility
        mSoftwareDetailsList = new ArrayList<>();
        mHardwareTypeList = new ArrayList<>();
        mAdminList = new ArrayList<>();
        List<String> resourcceCategory = new ArrayList<>();
        //Spinner dropdown options list
        resourcceCategory.add(getString(R.string.tap_to_select));
        resourcceCategory.add(getString(R.string.hardware_resource));
        resourcceCategory.add(getString(R.string.software_resource));
        //Creating adapter for spinner
        mRowItemsList = new ArrayList<RowItem>();
        for (int i = 0; i < resourcceCategory.size(); i++) {
            RowItem item = new RowItem(resourcceCategory.get(i));
            mRowItemsList.add(item);
            CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(NewRequestActivity.this,
                    R.layout.customized_spinner_dropdown, R.id.title, mRowItemsList);
            mSpnrResourceCategory.setAdapter(adapter);
        }
        fillSpinners();
        fillAdminSpinner();
        mTxtIssueDate.setOnClickListener(this);
        (findViewById(R.id.request_resource_button)).setOnClickListener(this);
    }

    private void fillSpinners() {
        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();

        Call<SoftwareDetailsSetter> call = apiCallHandler.getInterface().getSoftwareDetails(LOGIN_REQUEST_HEADER_VALUE, token);
        apiCallHandler.getResponse(call, ApiCallHandler.KEY_SOFTWARE_SUBCAT, this, this);
//        final ProgressDialog progressDialog = new ProgressDialog(NewRequestActivity.this);
//        progressDialog.setMessage(getString(R.string.loading));
//        progressDialog.show();
//       // final String mToken = mSharedPreferences.getString(TOKEN, "");
//        CallAPIInterface callAPIInterface = ServiceGenerator.createService(CallAPIInterface.class);
//        LoggerUtility.log(TAG, "Retrofit object created in updateUserProfile");
//        IdSetter softwareDetailIdSetter = new IdSetter();
//        //softwareDetailIdSetter.setId(mSoftwareDetailsList.get(mSpnrSubCategory.getSelectedItemPosition()).getID());
//        Call<SoftwareDetailsSetter> call = callAPIInterface.getSoftwareDetails(LOGIN_REQUEST_HEADER_VALUE, mToken);
//        LoggerUtility.log(TAG, "response in call object in UpdateUserProfile");
//        call.enqueue(new Callback<SoftwareDetailsSetter>() {
//                         @Override
//                         public void onResponse(Call<SoftwareDetailsSetter> call, Response<SoftwareDetailsSetter> response) {
//                             //response's body
//                             LoggerUtility.log(TAG, "Response body");
//                             progressDialog.dismiss();
//                             if (response.body().getResponse().getCode() == OK) {
//                                 mSoftwareDetailsList = response.body().getSoftwareDetailsList();
//                             }
//                             if (response.body().getResponse().getCode() == NOT_FOUND) {
//                                 new AlertDialog.Builder(getApplicationContext())
//                                         .setTitle(getString(R.string.server_error))
//                                         .setMessage(getString(R.string.unable_to_connect))
//                                         .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                                             public void onClick(DialogInterface dialog, int which) {
//                                                 Intent i = new Intent(getApplicationContext(), HomeActivity.class);
//                                                 startActivity(i);
//                                             }
//                                         })
//                                         .setIcon(android.R.drawable.ic_dialog_alert)
//                                         .show();
//                             }
//                         }
//
//                         @Override
//                         public void onFailure(Call<SoftwareDetailsSetter> call, Throwable t) {
//                             LoggerUtility.log(TAG, "Error requesting API");
//                         }
//                     }
//        );

        Call<HardwareTypeResponse> callType = apiCallHandler.getInterface().getMasterHardwareTypes(LOGIN_REQUEST_HEADER_VALUE, token);
        apiCallHandler.getResponse(callType, ApiCallHandler.KEY_HARDWARE_SUBCAT, this, this);
//        Call<HardwareTypeResponse> callType = callAPIInterface.getMasterHardwareTypes(LOGIN_REQUEST_HEADER_VALUE, mToken);
//        LoggerUtility.log(TAG, "response in call object in hardware type detailse");
//        callType.enqueue(new Callback<HardwareTypeResponse>() {
//                             @Override
//                             public void onResponse(Call<HardwareTypeResponse> call, Response<HardwareTypeResponse> response) {
//                                 //response's body
//                                 LoggerUtility.log(TAG, "Response body");
//                                 if (response.body().getResponse().getCode() == OK) {
//                                     mHardwareTypeList = response.body().getHardwareTypeSetter();
//                                 }
//                             }
//
//                             @Override
//                             public void onFailure(Call<HardwareTypeResponse> call, Throwable t) {
//                                 LoggerUtility.log(TAG, "Error requesting API");
//                             }
//                         }
//        );

        Call<AdminListResponse> callAdmin = apiCallHandler.getInterface().getAdminList(LOGIN_REQUEST_HEADER_VALUE, token);
        apiCallHandler.getResponse(callAdmin, ApiCallHandler.KEY_FILL_ADMIN, this, this);
//        Call<AdminListResponse> callAdmin = callAPIInterface.getAdminList(LOGIN_REQUEST_HEADER_VALUE, mToken);
//        LoggerUtility.log(TAG, "response in call object in hardware type detailse");
//        callAdmin.enqueue(new Callback<AdminListResponse>() {
//                              @Override
//                              public void onResponse(Call<AdminListResponse> call, Response<AdminListResponse> response) {
//                                  //response's body
//                                  LoggerUtility.log(TAG, "Response body");
//                                  if (response.body().getResponse().getCode() == OK) {
//                                      mAdminList = response.body().getAdminListSetter();
//                                      fillAdminSpinner();
//                                  }
//                              }
//
//                              @Override
//                              public void onFailure(Call<AdminListResponse> call, Throwable t) {
//                                  LoggerUtility.log(TAG, "Error requesting API");
//                              }
//                          }
//        );
        UserIdSetter idSetter = new UserIdSetter();
        idSetter.setUserID(Integer.parseInt(mSharedPref.getSharedPreferences(USERID)));
        Call<HardwareListForUserResponse> callHardware = apiCallHandler.getInterface().getHardwareForUser(LOGIN_REQUEST_HEADER_VALUE, token, idSetter);
        apiCallHandler.getResponse(callHardware, ApiCallHandler.KEY_FILL_HARDWARE, this, this);


    }

    private void fillAdminSpinner() {
        List<String> adminListArray = new ArrayList<>();
        String fullname = "";
        int i = 0;
        adminListArray.add(i, getString(R.string.tap_to_select));
        for (i = 0; i < mAdminList.size(); i++) {
            fullname = mAdminList.get(i).getFirstname() + " " + mAdminList.get(i).getLastname();
            adminListArray.add(i + 1, fullname);
        }
        mRowItemsList = new ArrayList<RowItem>();
        for (i = 0; i < adminListArray.size(); i++) {
            RowItem item = new RowItem(adminListArray.get(i));
            mRowItemsList.add(item);
            CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(NewRequestActivity.this,
                    R.layout.customized_spinner_dropdown, R.id.title, mRowItemsList);
            mSpnrSelectAdmin.setAdapter(adapter);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        if (parent.getId() == R.id.category_spinner) {
            switch (position) {
                case 1:
                    fillWithHardwareTypeList();
                    break;
                case 2:
                    fillWithSoftwareName();
                    break;
            }
        }
        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), "Selected: " + position + " position", Toast.LENGTH_SHORT).show();
    }

    private void fillWithSoftwareName() {
        (findViewById(R.id.user_req_hardware_for_software)).setVisibility(View.VISIBLE);

        mRowItemsList = new ArrayList<>();
        mRowItemsList.add(new RowItem(getString(R.string.tap_to_select)));
        for (SoftwareDetails software : mSoftwareDetailsList) {
            mRowItemsList.add(new RowItem(software.getSoftwareName()));
        }
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(NewRequestActivity.this,
                R.layout.customized_spinner_dropdown, R.id.title, mRowItemsList);
        mSpnrSubCategory.setAdapter(adapter);

        List<RowItem> hardwareList = new ArrayList<>();
        hardwareList.add(new RowItem(getString(R.string.tap_to_select)));
        for (AssignedHardware hardware : mAssignedHardwareList) {
            hardwareList.add(new RowItem(hardware.getModel()));
        }
        adapter = new CustomSpinnerAdapter(NewRequestActivity.this,
                R.layout.customized_spinner_dropdown, R.id.title, hardwareList);
        mSpnrSelectHardware.setAdapter(adapter);
    }


    private void fillWithHardwareTypeList() {
        List<String> hardwareTypeArray = new ArrayList<>();
        int i = 0;
        (findViewById(R.id.user_req_hardware_for_software)).setVisibility(View.GONE);
        mRowItemsList = new ArrayList<RowItem>();
        hardwareTypeArray.add(i, getString(R.string.tap_to_select));
        for (i = 0; i < mHardwareTypeList.size(); i++) {
            hardwareTypeArray.add(i + 1, mHardwareTypeList.get(i).getType());
        }
        for (i = 0; i < hardwareTypeArray.size(); i++) {
            RowItem item = new RowItem(hardwareTypeArray.get(i));
            mRowItemsList.add(item);
            CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(NewRequestActivity.this,
                    R.layout.customized_spinner_dropdown, R.id.title, mRowItemsList);
            mSpnrSubCategory.setAdapter(adapter);
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void initViews() {
        mTxtIssueDate = (TextView) findViewById(R.id.issued_date);
        mEdtTittle = (EditText) findViewById(R.id.resource_tittle);
        mEdtReqestDescription = (EditText) findViewById(R.id.requestDescription);
        mSpnrResourceCategory = (Spinner) findViewById(R.id.category_spinner);
        mSpnrSubCategory = (Spinner) findViewById(R.id.subcategory_spinner);
        mSpnrSelectAdmin = (Spinner) findViewById(R.id.admin_list_spinner);
        mSpnrSelectHardware = (Spinner) findViewById(R.id.hardware_spinner);

        mSpnrResourceCategory.setOnItemSelectedListener(this);
        mSpnrSubCategory.setOnItemSelectedListener(this);
        mSpnrSelectAdmin.setOnItemSelectedListener(this);
        mSpnrSelectHardware.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.issued_date:
                setDateTimeField();
                mDatePickerDialog.show();
                break;

            case R.id.request_resource_button:
                if (validfields()) {
                    makeAddNewUserRequestApiCall();
                }
        }
    }

    private void makeAddNewUserRequestApiCall() {
        int pos;
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        final String token = CheckForExpiry.checkForToken(this);
        NewUserRequestSetter newUserRequestSetter = new NewUserRequestSetter();
        newUserRequestSetter.setTitle(mEdtTittle.getText().toString());
        pos = mSpnrResourceCategory.getSelectedItemPosition();
        switch (pos) {
            case 1:
                newUserRequestSetter.setType(1);
                pos = mSpnrSubCategory.getSelectedItemPosition();
                newUserRequestSetter.setRequestedDevice(mHardwareTypeList.get(pos).getId()-1);
                break;
            case 2:
                newUserRequestSetter.setType(2);
                pos = mSpnrSubCategory.getSelectedItemPosition();
                newUserRequestSetter.setRequestedDevice(mSoftwareDetailsList.get(pos).getID()-1);

                pos = mSpnrSelectHardware.getSelectedItemPosition();
                newUserRequestSetter.setHardwareID(mAssignedHardwareList.get(pos).getID());
                break;
        }

        newUserRequestSetter.setToDate(mTxtIssueDate.getText().toString());
        pos = mSpnrSelectAdmin.getSelectedItemPosition();
        newUserRequestSetter.setAssignedTo(mAdminList.get(pos-1).getId());
        newUserRequestSetter.setDescription(mEdtReqestDescription.getText().toString());
        //SharedPreferences sharedPreferences = this.getSharedPreferences(USERPREFERENCES, Context.MODE_PRIVATE);

        newUserRequestSetter.setUserId(Integer.parseInt(mSharedPref.getSharedPreferences(USERID)));

        // TODO MAKE API CALL //done
        LoggerUtility.log(TAG, "Tittle : " + newUserRequestSetter.getTitle());
        LoggerUtility.log(TAG, "Category : " + newUserRequestSetter.getType());
        LoggerUtility.log(TAG, "Requested Device : " + newUserRequestSetter.getRequestedDevice());
        LoggerUtility.log(TAG, "Issue Date : " + newUserRequestSetter.getToDate());
        LoggerUtility.log(TAG, "Assigned to : " + newUserRequestSetter.getAssignedTo());
        LoggerUtility.log(TAG, "Description : " + newUserRequestSetter.getDescription());
        LoggerUtility.log(TAG, "User ID : " + newUserRequestSetter.getUserId());


//        CallAPIInterface callAPIInterface = ServiceGenerator.createService(CallAPIInterface.class);
//        LoggerUtility.log(TAG, "Retrofit object created");
        Call<ResponseGetterBase> call = apiCallHandler.getInterface().newUserRequestResponse(LOGIN_REQUEST_HEADER_VALUE, token, newUserRequestSetter);
        apiCallHandler.getResponse(call, ApiCallHandler.KEY_USER_NEW_REQ, this, this);
//        Call<ResponseGetterBase> call = callAPIInterface.newUserRequestResponse(LOGIN_REQUEST_HEADER_VALUE, mToken, newUserRequestSetter);
//        LoggerUtility.log(TAG, "response in call object");
//        call.enqueue(new Callback<ResponseGetterBase>() {
//            @Override
//            public void onResponse(Call<ResponseGetterBase> call, Response<ResponseGetterBase> response) {
//                //response's body
//                progressDialog.dismiss();
//                try {
//                    LoggerUtility.log(TAG, "Response body");
//                    if (response.body().getCode() == OK) {
//                        LoggerUtility.toastLong(getApplicationContext(), response.body().getMessage());
//                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
//                        NewRequestActivity.this.finish();
//                        startActivity(i);
//                        LoggerUtility.log(TAG, "Validated and Sucessfully Added.");
//                    } else if (response.body().getCode() == BAD_REQUEST) {
//                        LoggerUtility.toastShort(getApplicationContext(), "Invalid ResponseGetterBase");
//                        LoggerUtility.toastLong(getApplicationContext(), response.body().getMessage());
//                    }
//                } catch (Exception e) {
//                    LoggerUtility.toastLong(getApplicationContext(), getString(R.string.error_requesting_api));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseGetterBase> call, Throwable t) {
//                progressDialog.dismiss();
//                LoggerUtility.log(TAG, "Error requesting API");
//                t.printStackTrace();
//
//            }
//        });

    }

    private void setDateTimeField() {           //function to set date in mTxtIssueDate using the date picker
        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mTxtIssueDate.setText(mDateFormatter.format(newDate.getTime()));

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private boolean validfields() {
        if (GeneralTextUtils.getTextFromView(mEdtTittle).length() == 0) {
            GeneralTextUtils.requestFocusIfError(mEdtTittle, getString(R.string.error_field_required), TAG, "No Value for Title field");
            return false;
        } else if (GeneralTextUtils.getTextFromView(mTxtIssueDate).length() == 0) {
            GeneralTextUtils.requestFocusIfError(mTxtIssueDate, getString(R.string.error_field_required), TAG, "No Value for Issue Date");
            return false;
        } else return !spinnerHaveError();
    }

    private boolean spinnerHaveError() {
        int pos = mSpnrResourceCategory.getSelectedItemPosition();
        if (pos == 0) {
            mSpnrResourceCategory.performClick();
            LoggerUtility.toastLong(NewRequestActivity.this, getString(R.string.prompt_select_whom_to_request));
            return true;
        }
        pos = mSpnrSubCategory.getSelectedItemPosition();
        if (pos == 0) {
            mSpnrSubCategory.performClick();
            LoggerUtility.toastShort(NewRequestActivity.this, getString(R.string.prompt_select_subcategory));
            return true;
        }
        pos = mSpnrSelectAdmin.getSelectedItemPosition();
        if (pos == 0) {
            mSpnrSelectAdmin.performClick();
            LoggerUtility.toastLong(NewRequestActivity.this, getString(R.string.prompt_select_whom_to_request));
            return true;
        }
        return false;
    }

    private void makeDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame_activity_layout);
        //set the layout inside coordinator layout's frameLayout
        getLayoutInflater().inflate(R.layout.activity_new_request, frameLayout); //activity_user_requests
        LoggerUtility.log(TAG, "Toolbar created");
        DRAWER.setDrawerToggle(findViewById(R.id.drawer_layout), toolbar, this, findViewById(R.id.nav_view));
        LoggerUtility.log(TAG, "after setDrawerToggle");
        toolbar.setTitle(getString(R.string.resource) + " " + getString(R.string.manager));                    //TODO Remove hard coding
        toolbar.setSubtitle(getString(R.string.propmt_my_new_request));
        changeNavigationView(findViewById(R.id.nav_view));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            DRAWER.onBackPressed(drawerLayout);
        } else
            super.onBackPressed();
    }

    void changeNavigationView(View view) {
        NavigationView navigationView = (NavigationView) view;
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_home_drawer);
        //change header text to admin
        View admin_view = navigationView.getHeaderView(0);
        TextView admin_txt = (TextView) admin_view.findViewById(R.id.name_user);
        //TODO complete admin name here
        admin_txt.setText(SharedPref.getInstance(this).getSharedPreferences(SharedPref.NAMEKEY)); //Done
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return DRAWER.onNavigationItemSelectedUser(item, this, findViewById(R.id.drawer_layout));
    }

    @Override
    public void onSuccessResult(Object result, String key) {
        switch (key) {
            case (ApiCallHandler.KEY_SOFTWARE_SUBCAT):
                callbackSoftwareSubcat(result);
                break;

            case (ApiCallHandler.KEY_HARDWARE_SUBCAT):
                callbackHardwareSubcat(result);
                break;

            case (ApiCallHandler.KEY_FILL_ADMIN):
                callbackFillAdmin(result);
                break;

            case (ApiCallHandler.KEY_USER_NEW_REQ):
                callbackUserReq(result);
                break;

            case (ApiCallHandler.KEY_FILL_HARDWARE):
                callbackFillHardware(result);
                break;

            default:
                LoggerUtility.log(TAG, "No matching choice");
        }
    }

    /**
     * handle positive callback to Software Subcategory details
     *
     * @param result of positive callback
     */
    private void callbackSoftwareSubcat(Object result) {
        SoftwareDetailsSetter response = (SoftwareDetailsSetter) result;
        try {
            if (response.getResponse().getCode() == OK) {
                LoggerUtility.toastShort(this, response.getResponse().getMessage());
                mSoftwareDetailsList = response.getSoftwareDetailsList();
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastLong(this, getString(R.string.error_requesting_api));
        }
    }

    /**
     * handle positive callback to Hardware Subcategory details
     *
     * @param result of positive callback
     */
    private void callbackHardwareSubcat(Object result) {
        HardwareTypeResponse response = (HardwareTypeResponse) result;
        try {
            if (response.getResponse().getCode() == OK) {
                LoggerUtility.toastShort(this, response.getResponse().getMessage());
                mHardwareTypeList = response.getHardwareTypeSetter();
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastLong(this, getString(R.string.error_requesting_api));
        }
    }

    /**
     * handle positive callback to Admin details
     *
     * @param result of positive callback
     */
    private void callbackFillAdmin(Object result) {
        AdminListResponse response = (AdminListResponse) result;
        try {
            if (response.getResponse().getCode() == OK) {
                LoggerUtility.toastShort(this, response.getResponse().getMessage());
                mAdminList = response.getAdminListSetter();
                fillAdminSpinner();
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastLong(this, getString(R.string.error_requesting_api));
        }
    }

    /**
     * handle positive callback to Admin details
     *
     * @param result of positive callback
     */
    private void callbackFillHardware(Object result) {
        HardwareListForUserResponse response = (HardwareListForUserResponse) result;
        try {
            if (response.getResponse().getCode() == OK) {
                LoggerUtility.toastShort(this, response.getResponse().getMessage());
                mAssignedHardwareList = response.getAssignedHardwares();
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastLong(this, getString(R.string.error_requesting_api));
        }
    }

    /**
     * handle positive callback to Admin details
     *
     * @param result of positive callback
     */
    private void callbackUserReq(Object result) {
        ResponseGetterBase response = (ResponseGetterBase) result;
        try {
            if (response.getCode() == OK) {
                LoggerUtility.toastShort(this, response.getMessage());
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                NewRequestActivity.this.finish();
                startActivity(i);
            }
            else
            {
                LoggerUtility.toastLong(this, response.getMessage());
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastLong(this, getString(R.string.error_requesting_api));
        }
    }
}
