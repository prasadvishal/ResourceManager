package com.mindfiresolutions.resourcemanager.commonAdminUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.launcherPackage.LoginActivity;
import com.mindfiresolutions.resourcemanager.utility.InternetConnections;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;

/**
 * Created By: Vishal on: 3/30/2017
 * Last modified on: 4/01/2017
 */

public class ChangePasswordActivity extends AppCompatActivity {
    private String TAG = this.getClass().getName();
    private EditText mOldPassword, mNewPassword, mCnfrmNewPassword;
    private Button mChngPswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mOldPassword = (EditText) findViewById(R.id.ChgPwd_oldpswd);
        mNewPassword = (EditText) findViewById(R.id.ChgPwd_newpswd);
        mCnfrmNewPassword = (EditText) findViewById(R.id.ChgPwd_cnfrmpswd);
        mChngPswd = (Button) findViewById(R.id.ChgPwd_Button);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        //check for Actionbar if present then set tittle
        if (ab != null && ab.isShowing()) {
            ab.setTitle("Resource Manager");
            ab.setSubtitle("Edit Profile");
            ab.setDisplayHomeAsUpEnabled(true);
        }
        if (InternetConnections.checkInternetConnection(getApplicationContext())) {
            LoggerUtility.log(TAG, "INTERNET connection available");
            mChngPswd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    SharedPreferences sharedPreferences = getSharedPreferences(USERPREFERENCES, Context.MODE_PRIVATE);
//                    final String userNameInSharedPreference = sharedPreferences.getString(NAMEKEY, "");
//                    final String passwordInSharedPreference = sharedPreferences.getString(PASSWORDKEY, "");
//                    ChangePasswordSetter changePasswordSetter = new ChangePasswordSetter();
//                    changePasswordSetter.setUserName(userNameInSharedPreference);
//                    if (mOldPassword.getText().toString().equals(passwordInSharedPreference)) {
//                        changePasswordSetter.setPassword(mOldPassword.getText().toString());
//                    } else {
//                        mOldPassword.setError(getString(R.string.old_password_mismatch));
//                        mOldPassword.requestFocus();
//                        return;
//                    }
                    if (!mNewPassword.getText().toString().isEmpty() || !mCnfrmNewPassword.getText().toString().isEmpty() || mNewPassword.getText().toString().length() < 4)
                        if (mNewPassword.getText().toString().equals(mCnfrmNewPassword.getText().toString())) {
//                            changePasswordSetter.setNewPassword(mNewPassword.getText().toString());
//                            final String token = SharedPref.getSharedPreferences(getApplicationContext(),TOKEN);
//                            final ProgressDialog progressDialog = new ProgressDialog(ChangePasswordActivity.this);
//                            progressDialog.setMessage(getString(R.string.loading));
//                            progressDialog.show();
//                            progressDialog.setCancelable(false);
//                            progressDialog.setCanceledOnTouchOutside(false);
//                            CallAPIInterface callAPIInterface = ServiceGenerator.createService(CallAPIInterface.class);
//                            LoggerUtility.log(TAG, "Retrofit object created");
//                            Call<ResponseGetterBase> call = callAPIInterface.getResponse(LOGIN_REQUEST_HEADER_VALUE, token, changePasswordSetter);
//                            LoggerUtility.log(TAG, "response in call object");
//                            call.enqueue(new Callback<ResponseGetterBase>() {
//                                @Override
//                                public void onResponse(Call<ResponseGetterBase> call, Response<ResponseGetterBase> response) {
//                                    //response's body
//                                    progressDialog.dismiss();
//                                    LoggerUtility.log(TAG, "Response body");
//                                    try {
//                                        if (response.body().getCode() == OK) {
//                                            progressDialog.dismiss();
//                                            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.successful_password_change));
//                                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
//                                            startActivity(i);
//                                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//                                            LoggerUtility.log(TAG, "Password Change Sucessfully.");
//                                        } else {
//                                            LoggerUtility.toastShort(getApplicationContext(), response.body().getMessage());
//                                        }
//
//                                    } catch (Exception e) {
//                                        LoggerUtility.toastLong(getApplicationContext(), getString(R.string.error_requesting_api));
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseGetterBase> call, Throwable t) {
//                                    progressDialog.dismiss();
//                                    LoggerUtility.log(TAG, "Error requesting API");
//                                    progressDialog.dismiss();
//                                    t.printStackTrace();
//
//                                }
//                            });
                        } else {
                            mCnfrmNewPassword.setError(getString(R.string.error_cnfrm_password_mismatch));
                            mCnfrmNewPassword.requestFocus();
                        }
                    else {
                        LoggerUtility.toastShort(getApplicationContext(), getString(R.string.prompt_empty_password_fields));
                    }

                }
            });
        } else {
            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.no_internet));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
