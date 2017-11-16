package com.mindfiresolutions.resourcemanager.admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.AddHardwareSetter;
import com.mindfiresolutions.resourcemanager.model.HardwareBrandResponse;
import com.mindfiresolutions.resourcemanager.model.HardwareBrandSetter;
import com.mindfiresolutions.resourcemanager.model.HardwareById;
import com.mindfiresolutions.resourcemanager.model.HardwareTypeResponse;
import com.mindfiresolutions.resourcemanager.model.HardwareTypeSetter;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterBase;
import com.mindfiresolutions.resourcemanager.resource.CustomSpinnerAdapter;
import com.mindfiresolutions.resourcemanager.resource.RowItem;
import com.mindfiresolutions.resourcemanager.utility.ApiCallHandler;
import com.mindfiresolutions.resourcemanager.utility.CallAPIInterface;
import com.mindfiresolutions.resourcemanager.utility.Drawer;
import com.mindfiresolutions.resourcemanager.utility.GeneralTextUtils;
import com.mindfiresolutions.resourcemanager.utility.InternetConnections;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;
import com.mindfiresolutions.resourcemanager.utility.ProgressDialogUtility;
import com.mindfiresolutions.resourcemanager.utility.ServiceGenerator;
import com.mindfiresolutions.resourcemanager.utility.SharedPref;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.HARDWARE_DETAILS;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.NAMEKEY;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.TOKEN;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.USERID;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.USERPREFERENCES;


/**
 * Created By: Vishal on 7th April
 * Last modified on 14th April
 */

public class AddHardwareActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Spinner mSpnrBrand, mSpnrHardwareType;
    private EditText mEdtHardwareDescription, mEdtMacId, mEdtSrvcTag, mEdtModelName;
    private TextView mTvOrderDateCreated, mTvReceivedDate;
    private Button mBtnAddHardware;
    private SimpleDateFormat mDateFormatter;
    private CheckBox mShareStatus;
    private DatePickerDialog mDatePickerDialog;
    private IntentIntegrator mQrScan;
    private Drawer mDrawer = new Drawer();
    private static final String TAG = AddHardwareActivity.class.getSimpleName();
    private List<HardwareBrandSetter> hardwareBandList;
    private List<HardwareTypeSetter> hardwareTypeList;
    private SharedPreferences mSharedPreferences;
    private ImageButton mScanCodeButton;
    private List<RowItem> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();

        setContentView(R.layout.activity_add_hardware_resource);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        //check for Actionbar if present then set tittle
        if (ab != null && ab.isShowing()) {
            ab.setTitle(getString(R.string.resource) + " " + getString(R.string.manager));
            ab.setSubtitle(getString(R.string.add_hardware));
            ab.setDisplayHomeAsUpEnabled(true);
        }
        findviewsbyids();
        hardwareBandList = new ArrayList<>();
        hardwareTypeList = new ArrayList<>();
        //fillTodayDate();
        clickListenerDate(mTvReceivedDate);
        clickListenerDate(mTvOrderDateCreated);
        mQrScan = new IntentIntegrator(this);
        mScanCodeButton.setOnClickListener(this);
        if (i.hasExtra(HARDWARE_DETAILS)) {
            HardwareById hardwareDetails = i.getExtras().getParcelable(HARDWARE_DETAILS);
            prefillDetails(hardwareDetails);
        }
        if (InternetConnections.checkInternetConnection(getApplicationContext())) {
            LoggerUtility.log(TAG, "INTERNET connection available");
            fillSpinner();
            mBtnAddHardware.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (mSpnrHardwareType.getSelectedItemPosition() == 0) {
                        LoggerUtility.toastShort(AddHardwareActivity.this, getString(R.string.prompt_hardware_type_selection));
                        mSpnrHardwareType.performClick();
                        return;
                    }
                    if (mSpnrBrand.getSelectedItemPosition() == 0) {
                        LoggerUtility.toastShort(AddHardwareActivity.this, getString(R.string.prompt_brand_selection));
                        mSpnrBrand.performClick();
                        return;
                    }
                    if (GeneralTextUtils.getTextFromView(mEdtModelName).length() == 0) {
                        GeneralTextUtils.requestFocusIfError(mEdtModelName, getString(R.string.error_field_required), TAG, "Validation Error: No value for Name");
                        mEdtModelName.setText(GeneralTextUtils.getTextFromView(mEdtModelName));
                        return;
                    }
                    if (GeneralTextUtils.getTextFromView(mEdtMacId).length() == 0) {
                        GeneralTextUtils.requestFocusIfError(mEdtMacId, getString(R.string.error_field_required), TAG, "Validation Error: No value for MAC ID");
                        mEdtMacId.setText(GeneralTextUtils.getTextFromView(mEdtMacId));
                        return;
                    }
                    if (GeneralTextUtils.getTextFromView(mEdtSrvcTag).length() == 0) {
                        GeneralTextUtils.requestFocusIfError(mEdtSrvcTag, getString(R.string.error_field_required), TAG, "Validation Error: No value for Service Tag");
                        mEdtSrvcTag.setText(GeneralTextUtils.getTextFromView(mEdtSrvcTag));
                        return;
                    }
                    if (GeneralTextUtils.getTextFromView(mTvOrderDateCreated).length() == 0) {
                        GeneralTextUtils.requestFocusIfError(mTvOrderDateCreated, getString(R.string.error_field_required), TAG, "Validation Error: No value for Ordered Date");
                        mTvOrderDateCreated.setText(GeneralTextUtils.getTextFromView(mTvOrderDateCreated));
                        return;
                    }
                    if (GeneralTextUtils.getTextFromView(mTvReceivedDate).length() == 0) {
                        GeneralTextUtils.requestFocusIfError(mTvOrderDateCreated, getString(R.string.error_field_required), TAG, "Validation Error: No value for Received Date");
                        mTvReceivedDate.setText(GeneralTextUtils.getTextFromView(mTvReceivedDate));
                    }

                    AddHardwareSetter addHardwareSetter = new AddHardwareSetter();
                    addHardwareSetter.setMacId(GeneralTextUtils.getTextFromView(mEdtMacId));
                    addHardwareSetter.setServiceTag(GeneralTextUtils.getTextFromView(mEdtSrvcTag));
                    addHardwareSetter.setOrderDate(GeneralTextUtils.getTextFromView(mTvOrderDateCreated));
                    addHardwareSetter.setReceivedDate(GeneralTextUtils.getTextFromView(mTvReceivedDate));
                    addHardwareSetter.setDescription(GeneralTextUtils.getTextFromView(mEdtHardwareDescription));
                    addHardwareSetter.setModel(GeneralTextUtils.getTextFromView(mEdtModelName));
                    if (mShareStatus.isChecked())
                        addHardwareSetter.setIsSharable(true);
                    else
                        addHardwareSetter.setIsSharable(false);

                    addHardwareSetter.setType(SpinnerSelectedStringToIdMap(mSpnrHardwareType));
                    addHardwareSetter.setBrand(SpinnerSelectedStringToIdMap(mSpnrBrand));

                    LoggerUtility.toastShort(AddHardwareActivity.this, mSpnrBrand.getSelectedItem().toString());
                    final int userIdInSharedPreference = Integer.parseInt(mSharedPreferences.getString(USERID, "")); //todo use sharedpref object
                    addHardwareSetter.setCreatedBy(userIdInSharedPreference);
                    final String token = mSharedPreferences.getString(TOKEN, "");
                    final ProgressDialog progressDialog = new ProgressDialog(AddHardwareActivity.this);
                    progressDialog.setMessage(getString(R.string.loading));
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    CallAPIInterface callAPIInterface = ServiceGenerator.createService(CallAPIInterface.class);
                    LoggerUtility.log(TAG, "Retrofit object created");
                    Call<ResponseGetterBase> call = callAPIInterface.addNewHardwareRequestResponse(LOGIN_REQUEST_HEADER_VALUE, token, addHardwareSetter);
                    LoggerUtility.log(TAG, "response in call object");
                    call.enqueue(new Callback<ResponseGetterBase>() {
                        @Override
                        public void onResponse(Call<ResponseGetterBase> call, Response<ResponseGetterBase> response) {
                            //response's body
                            progressDialog.dismiss();
                            LoggerUtility.log(TAG, "Response body");
                            try {
                                if (response.body().getCode() == OK) {
                                    LoggerUtility.toastShort(getApplicationContext(), getString(R.string.successfully_added_new_hardware));
                                    LoggerUtility.log(TAG, "Validated and Sucessfully Added New Hardware.");
                                    Intent i = new Intent(getApplicationContext(), AdminHomeMainActivity.class);
                                    AddHardwareActivity.this.finish();
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                } else {
                                    LoggerUtility.toastShort(AddHardwareActivity.this, response.body().getMessage());
                                }
                            } catch (NullPointerException e) {
                                LoggerUtility.toastLong(getApplicationContext(), getString(R.string.error_requesting_api));
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseGetterBase> call, Throwable t) {
                            progressDialog.dismiss();
                            LoggerUtility.log(TAG, "Error requesting API");
                            t.printStackTrace();
                        }
                    });
                }

            });
        } else {
            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.no_internet));
        }
    }

    private void prefillDetails(HardwareById hardwareDetails) {
        mEdtMacId.setText(hardwareDetails.getMACID());
        mEdtSrvcTag.setText(hardwareDetails.getServiceTag());
        mTvOrderDateCreated.setText(hardwareDetails.getOrderDate());
        mTvReceivedDate.setText(hardwareDetails.getReceivedDate());

    }

    private void makeDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_app_home);
        LoggerUtility.log(TAG, "outside Coordinator");
        final FrameLayout frameLayout = (FrameLayout) coordinatorLayout.findViewById(R.id.content_frame_activity_layout);
        getLayoutInflater().inflate(R.layout.activity_add_hardware_resource, frameLayout);
        LoggerUtility.log(TAG, "Toolbar created");
        mDrawer.setDrawerToggle(findViewById(R.id.drawer_layout), toolbar, this, findViewById(R.id.nav_view));
        LoggerUtility.log(TAG, "after setDrawerToggle");
        changeNavigationView(findViewById(R.id.nav_view));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(this, AdminHomeMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    void findviewsbyids() {
        mDateFormatter = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());
        mEdtHardwareDescription = (EditText) findViewById(R.id.new_hardware_rqst_desc);
        mEdtSrvcTag = (EditText) findViewById(R.id.add_new_hardware_srvctag);
        mEdtMacId = (EditText) findViewById(R.id.add_new_hardware_mac_id);
        mTvOrderDateCreated = (TextView) findViewById(R.id.add_new_hardware_order_date);
        mTvReceivedDate = (TextView) findViewById(R.id.add_new_hardware_rcvd_date);
        mShareStatus = (CheckBox) findViewById(R.id.add_new_hardware_shared_resource_chkbox);
        mBtnAddHardware = (Button) findViewById(R.id.add_new_hardware_add_hardware_button);
        mSharedPreferences = getSharedPreferences(USERPREFERENCES, Context.MODE_PRIVATE);
        mScanCodeButton = (ImageButton) findViewById(R.id.service_tag_scan_button);
        mEdtModelName = (EditText) findViewById(R.id.add_new_hardware_model_name);
    }


    private void clickListenerDate(final TextView dateTextView) {
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateTimeField(dateTextView.getId());
                mDatePickerDialog.show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
//
//    private void fillTodayDate() {
//        long date = System.currentTimeMillis();
//        LoggerUtility.log();(TAG, "inside fill TodayDate");
//        String SIMPLE_DATE_FORMAT = "MMM MM dd, yyyy h:mm a";
//        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.ENGLISH);
//        String dateString = sdf.format(date);
//        mCurrentDate.setText(dateString);
//    }

    private void setDateTimeField(final int viewId) {//function to set date in mIssueDate using the date picker
//        findViewById(viewId).setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ((TextView) findViewById(viewId)).setText(mDateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    int SpinnerSelectedStringToIdMap(Spinner spinner) {

        int id = 0 ;
        switch (spinner.getId()) {
            case (R.id.add_new_hardware_hardware_brand_spinner):
                id = hardwareBandList.get(spinner.getSelectedItemPosition() - 1).getId();
                break;

            case (R.id.add_new_hardware_hardware_type_spinner):
                id = hardwareTypeList.get(spinner.getSelectedItemPosition() - 1).getId();
                break;

            default:
                return 0;

        }
        return id;
    }

    void fillSpinner() {
        mSpnrBrand = (Spinner) findViewById(R.id.add_new_hardware_hardware_brand_spinner);
        mSpnrHardwareType = (Spinner) findViewById(R.id.add_new_hardware_hardware_type_spinner);
        mSpnrBrand.setOnItemSelectedListener(this);
        mSpnrHardwareType.setOnItemSelectedListener(this);
        ProgressDialogUtility.startProgressDialog(this);
        final String token = mSharedPreferences.getString(TOKEN, "");
//        progressDialog.setMessage(getString(R.string.loading));
//        progressDialog.show();
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);

        CallAPIInterface callAPIInterface = ServiceGenerator.createService(CallAPIInterface.class);
        LoggerUtility.log(TAG, "Retrofit object created in updateUserProfile");
        Call<HardwareBrandResponse> call = callAPIInterface.getMasterHardwareBrands(LOGIN_REQUEST_HEADER_VALUE, token);
        LoggerUtility.log(TAG, "response in call object in UpdateUserProfile");
        call.enqueue(new Callback<HardwareBrandResponse>() {
                         @Override
                         public void onResponse(Call<HardwareBrandResponse> call, Response<HardwareBrandResponse> response) {
                             //response's body
                             LoggerUtility.log(TAG, "Response body\n" + response.body().getHardwareBrandSetter().toString());
//                             progressDialog.dismiss();
                             ProgressDialogUtility.dismissProgressDialog();
                             if (response.body().getResponse().getCode() == OK) {
                                 hardwareBandList = response.body().getHardwareBrandSetter();
                                 List<String> hardwareBrandArray = new ArrayList<>();
                                 hardwareBrandArray.add(getString(R.string.tap_to_select));
                                 // Adding brand name in array using foreach loop
                                 for (HardwareBrandSetter brandSetter : hardwareBandList) {
                                     hardwareBrandArray.add(brandSetter.getBrand());
                                 }
                                 int i;
                                 rowItems = new ArrayList<RowItem>();
                                 for (i = 0; i < hardwareBrandArray.size(); i++) {
                                     RowItem item = new RowItem(hardwareBrandArray.get(i));
                                     rowItems.add(item);
                                     CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddHardwareActivity.this,
                                             R.layout.customized_spinner_dropdown, R.id.title, rowItems);
                                     mSpnrBrand.setAdapter(adapter);
                                 }
                             } else {
//                                 progressDialog.dismiss();
                                 ProgressDialogUtility.dismissProgressDialog();
                                 LoggerUtility.toastShort(getApplicationContext(), response.body().getResponse().getMessage());
                             }
                         }

                         @Override
                         public void onFailure(Call<HardwareBrandResponse> call, Throwable t) {
                             ProgressDialogUtility.dismissProgressDialog();
                             LoggerUtility.log(TAG, "Error requesting API");
                         }
                     }
        );

        Call<HardwareTypeResponse> callBrand = callAPIInterface.getMasterHardwareTypes(LOGIN_REQUEST_HEADER_VALUE, token);
        LoggerUtility.log(TAG, "response in call object in hardware type detailse");
        callBrand.enqueue(new Callback<HardwareTypeResponse>() {
                              @Override
                              public void onResponse(Call<HardwareTypeResponse> call, Response<HardwareTypeResponse> response) {
                                  //response's body
                                  LoggerUtility.log(TAG, "Response body");
                                  try {
                                      if (response.body().getResponse().getCode() == OK) {
                                          hardwareTypeList = response.body().getHardwareTypeSetter();
                                          List<String> hardwareTypeArray = new ArrayList<>();
                                          int i;
                                          hardwareTypeArray.add(getString(R.string.tap_to_select));
                                          for (i = 0; i < hardwareTypeList.size(); i++) {
                                              hardwareTypeArray.add(i + 1, hardwareTypeList.get(i).getType());
                                          }

                                          rowItems = new ArrayList<RowItem>();
                                          for (i = 0; i < hardwareTypeArray.size(); i++) {
                                              RowItem item = new RowItem(hardwareTypeArray.get(i));
                                              rowItems.add(item);
                                              CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddHardwareActivity.this,
                                                      R.layout.customized_spinner_dropdown, R.id.title, rowItems);
                                              mSpnrHardwareType.setAdapter(adapter);
                                          }
                                      } else {
                                          LoggerUtility.toastShort(getApplicationContext(), response.body().getResponse().getMessage());
                                      }
                                  } catch (Exception e) {
                                      LoggerUtility.toastLong(getApplicationContext(), response.body().getResponse().getMessage());
                                  }
                              }

                              @Override
                              public void onFailure(Call<HardwareTypeResponse> call, Throwable t) {
                                  LoggerUtility.log(TAG, "Error requesting API");
                              }
                          }
        );
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            mDrawer.onBackPressed(drawerLayout);
//        } else
        super.onBackPressed();
    }

    void changeNavigationView(View view) {
        NavigationView navigationView = (NavigationView) view;
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.admin_home_drawer_menu);
        //change header text to admin
        View admin_view = navigationView.getHeaderView(0);
        TextView admin_txt = (TextView) admin_view.findViewById(R.id.name_user);
        //TODO complete admin name here
        // admin_txt.setText(SharedPref.getSharedPreferences(this,NAMEKEY)); //Showing username
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return mDrawer.onNavigationItemSelectedAdmin(item, this, findViewById(R.id.drawer_layout));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                mEdtSrvcTag.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        //initiating the qr code scan
        mQrScan.initiateScan();
    }
}
