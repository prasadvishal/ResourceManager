package com.mindfiresolutions.resourcemanager.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.AddSoftwareSetter;
import com.mindfiresolutions.resourcemanager.model.AdminListResponse;
import com.mindfiresolutions.resourcemanager.model.AdminListSetter;
import com.mindfiresolutions.resourcemanager.model.NewBrandSetter;
import com.mindfiresolutions.resourcemanager.model.NewLicenseSetter;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterBase;
import com.mindfiresolutions.resourcemanager.model.SoftwareBrandDetailList;
import com.mindfiresolutions.resourcemanager.model.SoftwareBrandListResponse;
import com.mindfiresolutions.resourcemanager.model.SoftwareDetails;
import com.mindfiresolutions.resourcemanager.model.SoftwareLicenseList;
import com.mindfiresolutions.resourcemanager.model.SoftwareLicenseListResponse;
import com.mindfiresolutions.resourcemanager.model.UpdateSoftwareSetter;
import com.mindfiresolutions.resourcemanager.utility.ApiCallHandler;
import com.mindfiresolutions.resourcemanager.utility.CheckForExpiry;
import com.mindfiresolutions.resourcemanager.utility.GeneralTextUtils;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;
import com.mindfiresolutions.resourcemanager.utility.SharedPref;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_ADD_SOFTWARE;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_EDIT_SOFTWARE;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_FILL_ADMIN;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_FILL_BRAND;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_FILL_LICENCE;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_NEW_BRAND;
import static com.mindfiresolutions.resourcemanager.utility.ApiCallHandler.KEY_NEW_LICENSE;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.INTERNAL_SERVER_ERROR;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.SOFTWARE_COUNT;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.SOFTWARE_DETAILS;
import static com.mindfiresolutions.resourcemanager.utility.KeyValueConstants.SOFTWARE_ID;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.NAMEKEY;

/**
 * This activity opens when new software has to be added or any any software is being edited.
 * Created by: Shivangi on 7th April
 * Last Modified on: 05/30//2017
 */
public class AddSoftwareActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        View.OnClickListener, ApiCallHandler.Listener {

    private static final String TAG = AddSoftwareActivity.class.getSimpleName();
    private EditText mEdtSoftwareName, mEdtSoftwareDescription, mEdtSoftwareVersion, mEdtSoftwareCount, mEdtSoftwareLicenceKey;
    private Spinner mSpnrBrandDetailSpinner, mSpnrLicenceTypeSpinner, mSpnrSelectAdminSpinner;
    private SimpleDateFormat mDateFormatter;
    private int mSoftwareId;
    private DatePickerDialog mDatePickerDialog;
    private TextView mTxtPurchaseDate, mTxtValidUptoDate;
    private AddSoftwareSetter mAddSoftwareSetter;
    private UpdateSoftwareSetter mUpdateSoftwareSetter;
    private List<SoftwareBrandDetailList> mLvSoftwareBrandList;
    private List<SoftwareLicenseList> mLvSoftwareLicenceList;
    private List<AdminListSetter> mLvAdminList;
    private Intent mIntentEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_software_resource);
        initViews();
        ActionBar ab = getSupportActionBar();
        if (ab != null && ab.isShowing()) {
            ab.setTitle(getString(R.string.resource) + " " + getString(R.string.manager));
            ab.setSubtitle(getString(R.string.add_software));
            ab.setDisplayHomeAsUpEnabled(true);
        }
        mIntentEdit = getIntent();
        mUpdateSoftwareSetter = new UpdateSoftwareSetter();
        mAddSoftwareSetter = new AddSoftwareSetter();
        mLvSoftwareBrandList = new ArrayList<>();
        mLvSoftwareLicenceList = new ArrayList<>();

        mDateFormatter = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());
        mTxtPurchaseDate.setOnClickListener(this);
        mTxtValidUptoDate.setOnClickListener(this);

        fillTodayDate();
        fillSpinner();
        checkForEditMode();
    }

    //initialize various views
    private void initViews() {
        mTxtPurchaseDate = (TextView) findViewById(R.id.add_new_software_txt_purchase_date);
        mTxtValidUptoDate = (TextView) findViewById(R.id.add_new_software_txt_valid_upt);
        mEdtSoftwareName = (EditText) findViewById(R.id.add_new_software_edt_name);
        mEdtSoftwareVersion = (EditText) findViewById(R.id.add_new_software_edt_add_version);
        mEdtSoftwareDescription = (EditText) findViewById(R.id.add_new_software_edt_rqst_desc);
        mEdtSoftwareCount = (EditText) findViewById(R.id.add_new_software_edt_count);
        mEdtSoftwareLicenceKey = (EditText) findViewById(R.id.add_new_software_edt_licence_key);
        mSpnrLicenceTypeSpinner = (Spinner) findViewById(R.id.add_new_software_spnr_licence);
        mSpnrBrandDetailSpinner = (Spinner) findViewById(R.id.add_new_software_spnr_brand);
        mSpnrSelectAdminSpinner = (Spinner) findViewById(R.id.add_new_software_spnr_admin_list);
        mSpnrSelectAdminSpinner.setOnItemSelectedListener(this);
        mSpnrLicenceTypeSpinner.setOnItemSelectedListener(this);
        mSpnrBrandDetailSpinner.setOnItemSelectedListener(this);
    }

    /**
     * check if the activity is in edit software mode or placeholder new software mode
     */
    private void checkForEditMode() {
        Button addUpdateBtn = (Button) findViewById(R.id.add_new_software_btn_add_software);
        addUpdateBtn.setText(getString(R.string.add_software));
        addUpdateBtn.setOnClickListener(this);

        if (mIntentEdit.hasExtra(SOFTWARE_DETAILS)) {
            mIntentEdit = getIntent();
            addUpdateBtn.setText(getString(R.string.update));
            SoftwareDetails softwareDetails = mIntentEdit.getExtras().getParcelable(SOFTWARE_DETAILS);
            int totalCount = mIntentEdit.getExtras().getInt(SOFTWARE_COUNT);
            String count = Integer.toString(totalCount);
            mSoftwareId = mIntentEdit.getExtras().getInt(SOFTWARE_ID);
            mEdtSoftwareCount.setText(count);
            prefillDetails(softwareDetails);
        }
    }

    /**
     * set fields in pojo class in order to make request in api call
     */
    private void setUpdateSoftwareSetter() {
        mUpdateSoftwareSetter.setDescription(mEdtSoftwareDescription.getText().toString());
        mUpdateSoftwareSetter.setVersion(mEdtSoftwareVersion.getText().toString());
        mUpdateSoftwareSetter.setSoftwareName(mEdtSoftwareName.getText().toString());
        mUpdateSoftwareSetter.setLicenseType(mLvSoftwareLicenceList.get(mSpnrLicenceTypeSpinner.getSelectedItemPosition() -1).getID());
        mUpdateSoftwareSetter.setSoftwareBrand(mLvSoftwareBrandList.get(mSpnrBrandDetailSpinner.getSelectedItemPosition() -1).getID());
        mUpdateSoftwareSetter.setSoftwareID(mSoftwareId);
        mUpdateSoftwareSetter.setModifiedBy(SharedPref.getInstance(this).getSharedPreferences(NAMEKEY));
    }

    /**
     * if software is in edit mode, fill the necessary details in the corresponding fields
     *
     * @param softwareDetails details from previous activity
     */
    private void prefillDetails(SoftwareDetails softwareDetails) {

        mEdtSoftwareName.setText(softwareDetails.getSoftwareName());
        mEdtSoftwareVersion.setText(softwareDetails.getVersion());
        mEdtSoftwareLicenceKey.setText(softwareDetails.getLicenseTypeName());

        if(softwareDetails.getDescription() != null)
            mEdtSoftwareDescription.setText(softwareDetails.getDescription());
        mTxtPurchaseDate.setText(softwareDetails.getCreationDate());
    }

    /**
     * to validate fields befor making API call
     */
    private boolean validfields() {
        if (GeneralTextUtils.getTextFromView(mEdtSoftwareName).length() == 0) {
            GeneralTextUtils.requestFocusIfError(mEdtSoftwareName, getString(R.string.error_field_required), TAG, "Error: Software Name is Empty");
            return false;
        }
        if ((mSpnrBrandDetailSpinner.getSelectedItemPosition() == 0) || (mSpnrBrandDetailSpinner.getSelectedItemPosition() == mSpnrBrandDetailSpinner.getCount()-1)) {
            LoggerUtility.toastLong(this, getString(R.string.prompt_brand_selection));
            mSpnrBrandDetailSpinner.performClick();
            return false;
        }
        if (GeneralTextUtils.getTextFromView(mEdtSoftwareVersion).length() == 0) {
            GeneralTextUtils.requestFocusIfError(mEdtSoftwareVersion, getString(R.string.error_field_required), TAG, "Error: Software Version is Empty");
            return false;
        }
        if ((mSpnrLicenceTypeSpinner.getSelectedItemPosition() == 0)|| (mSpnrLicenceTypeSpinner.getSelectedItemPosition() == mSpnrLicenceTypeSpinner.getCount()-1)) {
            LoggerUtility.toastShort(this, getString(R.string.prompt_licence_type_selection));
            mSpnrLicenceTypeSpinner.performClick();
            return false;
        }
        if (GeneralTextUtils.getTextFromView(mEdtSoftwareCount).length() == 0) {
            GeneralTextUtils.requestFocusIfError(mEdtSoftwareCount, getString(R.string.error_field_required), TAG, "Error: Software Count is Empty");
            return false;
        }
        if (GeneralTextUtils.getTextFromView(mEdtSoftwareLicenceKey).length() == 0) {
            GeneralTextUtils.requestFocusIfError(mEdtSoftwareLicenceKey, getString(R.string.error_field_required), TAG, "Error: Software Licence Key is Empty");
            return false;
        }
        if (GeneralTextUtils.getTextFromView(mTxtPurchaseDate).length() == 0) {
            GeneralTextUtils.requestFocusIfError(mTxtPurchaseDate, getString(R.string.error_field_required), TAG, "Error: Purchase date is Empty");
            LoggerUtility.toastShort(this, getString(R.string.prompt_error_empty_purchase_date));
            return false;
        }
        if (GeneralTextUtils.getTextFromView(mTxtValidUptoDate).length() == 0) {
            GeneralTextUtils.requestFocusIfError(mTxtValidUptoDate, getString(R.string.error_field_required), TAG, "Error: No Value for Valid upto date");
            LoggerUtility.toastShort(this, getString(R.string.prompt_error_empty_valid_upto_date));
            return false;
        }
        if (mSpnrSelectAdminSpinner.getSelectedItemPosition() == 0) {
            LoggerUtility.toastShort(this, getString(R.string.nothing_selected));
            mSpnrSelectAdminSpinner.performClick();
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(this, AdminHomeActivity.class);
                this.finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.add_new_software_txt_purchase_date):
                setPurchaseDate();
                break;

            case (R.id.add_new_software_txt_valid_upt):
                setValidUptoDate();
                break;

            case (R.id.add_new_software_btn_add_software):
                addEditSoftware();
                break;

            default:
                LoggerUtility.log(TAG, "No options selected in onClick");
                break;
        }
    }

    /**
     * function to make datepicker to set valid upto date
     */
    private void setValidUptoDate() {
        TextView tvValidUpto = (TextView) findViewById(R.id.add_new_software_txt_valid_upt);
        setDateTimeField(R.id.add_new_software_txt_valid_upt);
        mDatePickerDialog.show();
        if (!(tvValidUpto.getText() == null))
            mAddSoftwareSetter.setValidUpto(tvValidUpto.getText().toString());
    }

    /**
     * function to placeholder or edit Software
     */
    private void addEditSoftware() {
        if (validfields()) {
            if (mIntentEdit.hasExtra(SOFTWARE_DETAILS)) {
                setUpdateSoftwareSetter();
                callEditSoftware();
            } else {
                setAddNewSoftwareResourceSetter();
                callAddNewSoftware();
            }
        }
    }

    /**
     * function to set purchase date
     */
    private void setPurchaseDate() {
        TextView tvPurchaseDate = (TextView) findViewById(R.id.add_new_software_txt_purchase_date);
        setDateTimeField(mTxtPurchaseDate.getId());
        mDatePickerDialog.show();
        if (!(tvPurchaseDate.getText() == null))
            mAddSoftwareSetter.setPurchasedDate(tvPurchaseDate.getText().toString());
    }

    /**
     * build alert dialog to placeholder new brand
     */
    private void addNewBrand() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        View dialog_layout;
        final ViewGroup nullParent = null;
        dialog_layout = getLayoutInflater().inflate(R.layout.add_new_software_dialog, nullParent);
        final EditText brandEdt = (EditText) dialog_layout.findViewById(R.id.add_new_software_dialog_edttxt_brand);
        final EditText descEdt = (EditText) dialog_layout.findViewById(R.id.add_new_software_dialog_edttxt_desc);
        alertDialog.setView(dialog_layout);
        alertDialog.show();
        dialog_layout.findViewById(R.id.add_new_software_dialog_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoggerUtility.log(TAG, "dialog button created");
                sendNewBrandDetails(brandEdt.getText().toString(), descEdt.getText().toString());
                alertDialog.dismiss();
            }
        });
    }

    /**
     * build alert dialog to placeholder new licence
     */
    private void addNewLicence() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        View dialog_layout;
        final ViewGroup nullParent = null;
        dialog_layout = getLayoutInflater().inflate(R.layout.add_new_software_license_type_dialog, nullParent);
        final EditText licenseEdt = (EditText) dialog_layout.findViewById(R.id.add_new_software_license_dialog_edttxt);
        alertDialog.setView(dialog_layout);
        alertDialog.show();
        dialog_layout.findViewById(R.id.add_new_software_license_dialog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoggerUtility.log(TAG, "dialog button created");
                sendNewLicenseDetails(licenseEdt.getText().toString());
                alertDialog.dismiss();
            }
        });
    }

    /**
     * mthod to get today's time
     */
    void fillTodayDate() {
        TextView todayDate = (TextView) findViewById(R.id.add_new_software_txt_curnt_date);
        long date = System.currentTimeMillis();
        LoggerUtility.log(TAG, "inside fill TodayDate");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a", Locale.ENGLISH);
        String dateString = sdf.format(date);
        todayDate.setText(dateString);
    }

    /**
     * method to fill data in admin spinner
     */
    private void fillAdminSpinner() {
        List<String> adminListArray = new ArrayList<>();
        int i = 0;
        String fullname;
        adminListArray.add(i, getString(R.string.tap_to_select));
        for (i = 1; i < mLvAdminList.size(); i++) {
            fullname = mLvAdminList.get(i - 1).getFirstname() + " " + mLvAdminList.get(i - 1).getLastname();
            adminListArray.add(i, fullname);
        }
        ArrayAdapter<String> adapterLicense = new ArrayAdapter<>(AddSoftwareActivity.this,
                android.R.layout.simple_spinner_dropdown_item, adminListArray);
        LoggerUtility.log(TAG, "Data filled in array adapter");
        adapterLicense.setDropDownViewResource(R.layout.customized_spinner_dropdown);
        mSpnrSelectAdminSpinner.setAdapter(adapterLicense);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //once spinner is selected this api will be called , parent is the spinner which is selected and mSelectedStringSpinner is the selected option
        LoggerUtility.log(TAG, "inside switch default case" + parent.getId());
        switch (parent.getId()) {
            //if selected string is placeholder new resource, placeholder new resource api will be called else set it to pojo
            case (R.id.add_new_software_spnr_licence):
                if (position == parent.getCount() - 1)
                    addNewLicence();
                break;

            case (R.id.add_new_software_spnr_brand):
                if (position == mSpnrBrandDetailSpinner.getCount() - 1)
                    addNewBrand();
                break;

            default:
                LoggerUtility.log(TAG, "inside switch default case" + parent.getId());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //if nothing selected by user
        LoggerUtility.log(TAG, "inside on nothing selected");
    }

    /**
     * function to set date in mIssueDate using the date picker
     *
     * @param viewId viewId of the view in which dateTimeField has to be set
     */
    private void setDateTimeField(final int viewId) {
        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ((TextView) findViewById(viewId)).setText(mDateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * set all the fields of pojo class
     */
    private void setAddNewSoftwareResourceSetter() {
        mAddSoftwareSetter.setCreatedBy(SharedPref.getInstance(this).getSharedPreferences(NAMEKEY));
        mAddSoftwareSetter.setDescription(mEdtSoftwareDescription.getText().toString());
        mAddSoftwareSetter.setVersion(mEdtSoftwareVersion.getText().toString());
        mAddSoftwareSetter.setSoftwareName(mEdtSoftwareName.getText().toString());
        mAddSoftwareSetter.setValidCount(Integer.parseInt(mEdtSoftwareCount.getText().toString()));
        mAddSoftwareSetter.setLicenseKeyValue(mEdtSoftwareLicenceKey.getText().toString());
        mAddSoftwareSetter.setPurchasedBy(mLvAdminList.get(mSpnrSelectAdminSpinner.getSelectedItemPosition() - 1).getId());                    // -1 because we have explicitly added 'tap to select' string on index 0
        mAddSoftwareSetter.setLicenseType(mLvSoftwareLicenceList.get(mSpnrLicenceTypeSpinner.getSelectedItemPosition() - 1).getID());
        mAddSoftwareSetter.setValidUpto(mTxtValidUptoDate.getText().toString());
        mAddSoftwareSetter.setPurchasedDate(mTxtPurchaseDate.getText().toString());
        mAddSoftwareSetter.setSoftwareBrand(mLvSoftwareBrandList.get(mSpnrBrandDetailSpinner.getSelectedItemPosition() - 1).getID());
    }

    /**
     * call api to placeholder new license details
     *
     * @param details sets licence details for new licence
     */
    private void sendNewLicenseDetails(String details) {
        NewLicenseSetter newLicenseSetter = new NewLicenseSetter();
        newLicenseSetter.setLicenseDetails(details);
        newLicenseSetter.setIsAvailable(true);

        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<ResponseGetterBase> call = apiCallHandler.getInterface().createMasterSoftwareLicenseType(LOGIN_REQUEST_HEADER_VALUE, token, newLicenseSetter);
        apiCallHandler.getResponse(call, KEY_NEW_LICENSE, this, this);
    }

    /**
     * after spinner option for new brand is selected send for new brand request
     *
     * @param brand name of the brand
     * @param desc  description of the brand
     */
    //TODO hit api again in spinner touch
    private void sendNewBrandDetails(String brand, String desc) {
        NewBrandSetter newBrandSetter = new NewBrandSetter();
        newBrandSetter.setBrand(brand);
        newBrandSetter.setDescription(desc);
        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<ResponseGetterBase> call = apiCallHandler.getInterface().createMasterSoftwareBrandType(LOGIN_REQUEST_HEADER_VALUE, token, newBrandSetter);
        apiCallHandler.getResponse(call, KEY_NEW_BRAND, this, this);
    }

    /**
     * fill spinner with the data coming from server
     */
    private void fillSpinner() {
        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        //fill Licence spinner
        Call<SoftwareLicenseListResponse> call = apiCallHandler.getInterface().getMasterSoftwareLicenseType(LOGIN_REQUEST_HEADER_VALUE, token);
        apiCallHandler.getResponse(call, KEY_FILL_LICENCE, this, this);

        //fill admin spinner
        Call<AdminListResponse> callAdmin = apiCallHandler.getInterface().getAdminList(LOGIN_REQUEST_HEADER_VALUE, token);
        apiCallHandler.getResponse(callAdmin, KEY_FILL_ADMIN, this, this);

        //fill brand spinner
        Call<SoftwareBrandListResponse> callBrand = apiCallHandler.getInterface().getMasterSoftwareBrandDetail(LOGIN_REQUEST_HEADER_VALUE, token);
        apiCallHandler.getResponse(callBrand, KEY_FILL_BRAND, this, this);
    }

    /**
     * funtion to placeholder new software
     */
    private void callAddNewSoftware() {
        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<ResponseGetterBase> call = apiCallHandler.getInterface().createNewSoftware(LOGIN_REQUEST_HEADER_VALUE,
                token, mAddSoftwareSetter);
        apiCallHandler.getResponse(call, KEY_ADD_SOFTWARE, this, this);
    }

    /**
     * function to edit the software
     */
    private void callEditSoftware() {
        final String token = CheckForExpiry.checkForToken(this);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<ResponseGetterBase> call = apiCallHandler.getInterface().getSoftwareUpdateResponse(LOGIN_REQUEST_HEADER_VALUE,
                token, mUpdateSoftwareSetter);
        apiCallHandler.getResponse(call, KEY_EDIT_SOFTWARE, this, this);
    }

    /**
     * function to handle all positive callbacks
     *
     * @param result positive callback's result
     * @param key    the key to determine which callback is returned
     */
    @Override
    public void onSuccessResult(Object result, String key) {
        switch (key) {
            case (ApiCallHandler.KEY_ADD_SOFTWARE):
                callbackAddEditSoftware(result);
                break;

            case (ApiCallHandler.KEY_EDIT_SOFTWARE):
                callbackAddEditSoftware(result);
                break;

            case (ApiCallHandler.KEY_FILL_ADMIN):
                callbackFillAdmin(result);
                break;

            case (ApiCallHandler.KEY_FILL_BRAND):
                callbackFillBrand(result);
                break;

            case (ApiCallHandler.KEY_FILL_LICENCE):
                callbackFillLicence(result);
                break;

            case (ApiCallHandler.KEY_NEW_BRAND):
                callbackNewLicenceBrand(result);
                break;

            case (ApiCallHandler.KEY_NEW_LICENSE):
                callbackNewLicenceBrand(result);
                break;

            default:
                LoggerUtility.log(TAG, "No matching choice");
        }
    }

    /**
     * handle positive callback to placeholder or edit software
     *
     * @param result of positive callback
     */
    private void callbackAddEditSoftware(Object result) {
        ResponseGetterBase response = (ResponseGetterBase) result;
        try {
            if (response.getCode() == OK) {
                LoggerUtility.toastShort(this, response.getMessage());
                startActivity(new Intent(this, AdminHomeActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
            else if (response.getCode() == INTERNAL_SERVER_ERROR) {
                LoggerUtility.toastLong(this, response.getMessage());
            }
            else
            {
                LoggerUtility.toastShort(this, response.getMessage());
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastLong(this, getString(R.string.error_requesting_api));
        }
    }

    /**
     * handle positive callback to fill licence in licence spinner
     *
     * @param result of positive callback
     */
    private void callbackFillLicence(Object result) {
        SoftwareLicenseListResponse response = (SoftwareLicenseListResponse) result;
        try {
            if (response.getResponse().getCode() == OK) {
                mLvSoftwareLicenceList = response.getSoftwareLicenseList();
                List<String> softwareLicenceTypeArray = new ArrayList<>();
                //placeholder hint at first index
                int i = 0;
                softwareLicenceTypeArray.add(i, getString(R.string.tap_to_select));
                //fill spinner then
                for (i = 0; i < mLvSoftwareLicenceList.size(); ++i) {
                    softwareLicenceTypeArray.add(i + 1, mLvSoftwareLicenceList.get(i).getLicenseDetails());
                }
                //fill last entry with placeholder new licence option
                softwareLicenceTypeArray.add(i + 1, getString(R.string.add_new_license));
                ArrayAdapter<String> adapterLicense = new ArrayAdapter<>(AddSoftwareActivity.this, android.R.layout.simple_spinner_dropdown_item, softwareLicenceTypeArray);
                LoggerUtility.log(TAG, "Data filled in array adapter");

                adapterLicense.setDropDownViewResource(R.layout.customized_spinner_dropdown);
                mSpnrLicenceTypeSpinner.setAdapter(adapterLicense);
            } else {
                LoggerUtility.toastShort(this, response.getResponse().getMessage());
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastShort(this, getString(R.string.error_requesting_api));
        }
    }

    /**
     * handle positive callback to fill admin in admin spinner
     *
     * @param result of positive callback
     */
    private void callbackFillAdmin(Object result) {
        AdminListResponse response = (AdminListResponse) result;
        try {
            LoggerUtility.log(TAG, "Response body");
            if (response.getResponse().getCode() == OK) {
                mLvAdminList = response.getAdminListSetter();
                fillAdminSpinner();
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastShort(this, getString(R.string.error_requesting_api));
            Log.e(TAG, "msg", e);

        }
    }

    /**
     * handle positive callback to fill brand name in spinner
     *
     * @param result result of positive callback
     */
    private void callbackFillBrand(Object result) {
        SoftwareBrandListResponse response = (SoftwareBrandListResponse) result;
        try {
            if (response.getResponse().getCode() == OK) {
                mLvSoftwareBrandList = response.getSoftwareBrandDetailsList();
                List<String> softwareBrandArray = new LinkedList<>();
                //placeholder hint at first index
                int i = 0;
                softwareBrandArray.add(i, getString(R.string.tap_to_select));
                //fill spinner then
                for (i = 0; i < mLvSoftwareBrandList.size(); i++) {
                    softwareBrandArray.add(i + 1, mLvSoftwareBrandList.get(i).getBrand());
                }
                //fill last entry with placeholder new licence option
                softwareBrandArray.add(i + 1, getString(R.string.add_new_brand));
                ArrayAdapter<String> adapterBrand = new ArrayAdapter<>(AddSoftwareActivity.this, android.R.layout.simple_spinner_dropdown_item, softwareBrandArray);
                LoggerUtility.log(TAG, "Data filled in array adapter");
                adapterBrand.setDropDownViewResource(R.layout.customized_spinner_dropdown);
                mSpnrBrandDetailSpinner.setAdapter(adapterBrand);
            } else {
                LoggerUtility.toastShort(this, response.getResponse().getMessage());
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastShort(this, getString(R.string.error_requesting_api));
        }
    }

    /**
     * handle positive callback to placeholder new licence and new brand
     *
     * @param result result of positive callback
     */
    private void callbackNewLicenceBrand(Object result) {
        ResponseGetterBase response = (ResponseGetterBase) result;
        try {
            if (response.getCode() == OK) {
                LoggerUtility.toastShort(this, response.getMessage());
            }
            else{
                LoggerUtility.toastShort(this, response.getMessage());
            }

        } catch (NullPointerException e) {
            LoggerUtility.toastLong(this, getString(R.string.error_requesting_api));
        }
    }
}