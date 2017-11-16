package com.mindfiresolutions.resourcemanager.launcherPackage;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterBase;
import com.mindfiresolutions.resourcemanager.model.SignUpSetter;
import com.mindfiresolutions.resourcemanager.utility.CallAPIInterface;
import com.mindfiresolutions.resourcemanager.utility.GeneralTextUtils;
import com.mindfiresolutions.resourcemanager.utility.InternetConnections;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;
import com.mindfiresolutions.resourcemanager.utility.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.BAD_REQUEST;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;


/**
 * Created By: Vishal on: 3/15/2017
 * Last modified on: 3/23/2017
 */
public class SignUpActivity extends AppCompatActivity {
    private final String TAG = SignUpActivity.class.getSimpleName();
    private EditText mEdtEmployeeID, mEdtFirstName, mEdtLastName, mEdtMobile, mEdtPassword, mEdtConfPassword, mEdtEmail, mEdtUsername;
    private CheckBox mChkAdminRegister;
    //private LoggerUtility mLoggerUtility = LoggerUtility.getLoggerUtility();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Initialization of EditText fields
        mEdtEmployeeID = (EditText) findViewById(R.id.employeeId);
        mEdtFirstName = (EditText) findViewById(R.id.fname);
        mEdtLastName = (EditText) findViewById(R.id.lname);
        mEdtMobile = (EditText) findViewById(R.id.mobileNumber);
        mEdtPassword = (EditText) findViewById(R.id.password);
        mEdtConfPassword = (EditText) findViewById(R.id.cnfrmPassword);
        mEdtEmail = (EditText) findViewById(R.id.email);
        mEdtUsername = (EditText) findViewById(R.id.username);
        mChkAdminRegister = (CheckBox) findViewById(R.id.register_admin);
        //Initialization of Register Button
        Button mRegisterButton = (Button) findViewById(R.id.register);
        //check for Actionbar if present then set tittle
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        //check for Actionbar if present then set tittle
        if (ab != null && ab.isShowing()) {
            ab.setTitle("Resource Manager");
            ab.setSubtitle("Sign Up");
            ab.setDisplayHomeAsUpEnabled(true);
        }
        mEdtPassword.setLongClickable(false);           // Deactivating Long clickable feature for password fields
        mEdtConfPassword.setLongClickable(false);       // Deactivating Long clickable feature for password fields
        LoggerUtility.log(TAG, " Ready for Validation");
        //Registration button functionality
        if (InternetConnections.checkInternetConnection(getApplicationContext())) {
            LoggerUtility.log(TAG, "INTERNET connection available");
            mRegisterButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (GeneralTextUtils.getTextFromView(mEdtUsername).length() == 0) { //Validating for No value
                        GeneralTextUtils.requestFocusIfError(mEdtUsername, getString(R.string.error_field_required), TAG, "Validation Error: No value for Employee ID");
                        mEdtUsername.setText(GeneralTextUtils.getTextFromView(mEdtUsername));
                        return;
                    }
                    if (GeneralTextUtils.getTextFromView(mEdtFirstName).length() == 0) { //Validating for No value
                        GeneralTextUtils.requestFocusIfError(mEdtFirstName, getString(R.string.error_field_required), TAG, "Validation Error: No value for Name");
                        mEdtFirstName.setText(GeneralTextUtils.getTextFromView(mEdtFirstName));
                        return;
                    }
                    if (GeneralTextUtils.getTextFromView(mEdtLastName).length() == 0) { //Validating for No value
                        GeneralTextUtils.requestFocusIfError(mEdtLastName, getString(R.string.error_field_required), TAG, "Validation Error: No value for Name");
                        mEdtLastName.setText(GeneralTextUtils.getTextFromView(mEdtLastName));
                        return;
                    }
                    if (GeneralTextUtils.getTextFromView(mEdtEmployeeID).length() == 0) { //Validating for No value
                        GeneralTextUtils.requestFocusIfError(mEdtEmployeeID, getString(R.string.error_field_required), TAG, "Validation Error: No value for Employee ID");
                        return;
                    }
                    if (GeneralTextUtils.getTextFromView(mEdtEmail).length() == 0) {      //Validating for No Email
                        GeneralTextUtils.requestFocusIfError(mEdtEmail, getString(R.string.error_field_required), TAG, "Validation Error: No value for Email ID");
                        return;
                    }
                    if (!(GeneralTextUtils.getTextFromView(mEdtEmail).contains("@")) || (!(GeneralTextUtils.getTextFromView(mEdtEmail).contains(".")))) {    //VAlidating for valid Email
                        GeneralTextUtils.requestFocusIfError(mEdtEmail, getString(R.string.error_invalid_email), TAG, "Validation Error: Invalid Email ID");
                        return;
                    }
                    if (!GeneralTextUtils.getTextFromView(mEdtMobile).isEmpty()) {
                        if ((GeneralTextUtils.getTextFromView(mEdtMobile).length()) != 10) { //Validating 10 digits in mobile number field
                            GeneralTextUtils.requestFocusIfError(mEdtMobile, getString(R.string.error_invalid_mobile));
                            return;
                        }
                    }
                    if (mEdtPassword.getText().toString().length() == 0) {           //Validating password for No Value
                        GeneralTextUtils.requestFocusIfError(mEdtPassword, getString(R.string.error_field_required), TAG, "Validation Error: No value for Password");
                        return;
                    }
                    if (mEdtPassword.getText().toString().length() < 4) {            //Validating password for length less than 4 characters
                        GeneralTextUtils.requestFocusIfError(mEdtPassword, getString(R.string.error_invalid_password), TAG, "Validation Error: Password too short");
                        return;
                    }
                    if (mEdtConfPassword.getText().toString().length() == 0) {       //Validating for no value in mEdtConfPassword
                        GeneralTextUtils.requestFocusIfError(mEdtConfPassword, getString(R.string.error_cnfrm_password), TAG, "Validation Error: No value for Confirm Password");
                        return;
                    }
                    if (!mEdtPassword.getText().toString().equals(mEdtConfPassword.getText().toString())) {   // Checking for value match
                        GeneralTextUtils.requestFocusIfError(mEdtConfPassword, getString(R.string.error_cnfrm_password_mismatch), TAG, "Validation Error: Conflicting values in Password and Confirm passowrd");
                        return;
                    }
                    if (mChkAdminRegister.isChecked()) {
                        LoggerUtility.toastLong(getApplicationContext(), getString(R.string.admin_registration_message));
                        LoggerUtility.log(TAG, "Request for admin registration");
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    } else {

                        SignUpSetter signupSetter = new SignUpSetter();
                        signupSetter.setUserName(mEdtUsername.getText().toString());
                        signupSetter.setPassword(mEdtPassword.getText().toString());
                        signupSetter.setFirstName(mEdtFirstName.getText().toString());
                        signupSetter.setLastName(mEdtLastName.getText().toString());
                        signupSetter.setEmployeeID(mEdtEmployeeID.getText().toString());
                        signupSetter.setEmail(mEdtEmail.getText().toString());
                        if (mChkAdminRegister.isChecked())
                            signupSetter.setRole(1);
                        else
                            signupSetter.setRole(2);
                        signupSetter.setContact(mEdtMobile.getText().toString());
                        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                        progressDialog.setMessage(getString(R.string.loading));
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);;
                        CallAPIInterface callAPIInterface = ServiceGenerator.createService(CallAPIInterface.class);
                        LoggerUtility.log(TAG, "Retrofit object created");
                        Call<ResponseGetterBase> call = callAPIInterface.signupResponse(LOGIN_REQUEST_HEADER_VALUE, signupSetter);
                        LoggerUtility.log(TAG, "response in call object");
                        call.enqueue(new Callback<ResponseGetterBase>() {
                            @Override
                            public void onResponse(Call<ResponseGetterBase> call, Response<ResponseGetterBase> response) {
                                //response's body
                                progressDialog.dismiss();
                                try {
                                    LoggerUtility.log(TAG, "Response body");
                                    if (response.body().getCode() == OK) {
                                        LoggerUtility.toastShort(getApplicationContext(), getString(R.string.successful_registeration));
                                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                        LoggerUtility.log(TAG, "Validated and Sucessfully Registered.");
                                    }
                                    if (response.body().getCode() == BAD_REQUEST) {
                                        LoggerUtility.toastShort(getApplicationContext(), "Invalid ResponseGetterBase");
                                    }
                                } catch (Exception e) {
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
                }

            });
        } else {
            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.no_internet));
        }
    }

}

