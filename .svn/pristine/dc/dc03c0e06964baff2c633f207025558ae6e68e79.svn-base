package com.mindfiresolutions.resourcemanager.launcherPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.ForgotPasswordSetter;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterBase;
import com.mindfiresolutions.resourcemanager.utility.ApiCallHandler;
import com.mindfiresolutions.resourcemanager.utility.InternetConnections;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;

import retrofit2.Call;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.BAD_REQUEST;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;

/**
 * This activity opens up when user clicks on forgot password
 * User enters the username, the password is send to registered email id
 * Created by Shivangi Singh on 4/16/2017
 * Last modified on: 5/31/2017
 */
public class ForgotPasswordActivity extends AppCompatActivity implements ApiCallHandler.Listener {

    private final String TAG = ForgotPasswordActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        findViewById(R.id.forgot_password_btn_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnections.checkInternetConnection(getApplicationContext())) {
                    LoggerUtility.log(TAG, "INTERNET connection available");
                    getUserNameForForgotPassword();
                } else {
                    LoggerUtility.toastShort(getApplicationContext(), getString(R.string.no_internet));
                }
            }
        });
    }

    //this function receives username from user and calls api with that user name to send password
    private void getUserNameForForgotPassword() {
        EditText userName = (EditText) findViewById(R.id.edt_username_edt_forgot_pswd);
        String usrName = userName.getText().toString();
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        ForgotPasswordSetter forgotPasswordSetter = new ForgotPasswordSetter();
        forgotPasswordSetter.setUserName(usrName);
        Call<ResponseGetterBase> call = apiCallHandler.getInterface().forgotPasswordRequest(LOGIN_REQUEST_HEADER_VALUE, forgotPasswordSetter);
        apiCallHandler.getResponse(call, null, this, this);
    }

    @Override
    public void onSuccessResult(Object result, String KEY) {
        ResponseGetterBase response = (ResponseGetterBase) result;
        try {
            LoggerUtility.log(TAG, "Response body");
            if (response.getCode() == OK) {
                LoggerUtility.log(TAG, "User name recieved and password send to registered id");
                LoggerUtility.toastShort(getApplicationContext(), getString(R.string.password_send_to_registered_id));
                Intent i = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                ForgotPasswordActivity.this.finish();
                startActivity(i);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
            if (response.getCode() == BAD_REQUEST) {
                LoggerUtility.toastShort(getApplicationContext(), getString(R.string.invalid_user));
            } else {
                LoggerUtility.toastShort(getApplicationContext(), response.getMessage());
            }
        } catch (NullPointerException e) {
            LoggerUtility.toastLong(getApplicationContext(), getString(R.string.error_requesting_api));
        }
    }
}
