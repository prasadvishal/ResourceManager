package com.mindfiresolutions.resourcemanager.launcherPackage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.admin.AdminHomeActivity;
import com.mindfiresolutions.resourcemanager.model.LoginSetter;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterWithToken;
import com.mindfiresolutions.resourcemanager.user.userHome.HomeActivity;
import com.mindfiresolutions.resourcemanager.utility.AlarmTokenCleanerReceiver;
import com.mindfiresolutions.resourcemanager.utility.ApiCallHandler;
import com.mindfiresolutions.resourcemanager.utility.GeneralTextUtils;
import com.mindfiresolutions.resourcemanager.utility.InternetConnections;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;
import com.mindfiresolutions.resourcemanager.utility.SharedPref;

import retrofit2.Call;

import static com.mindfiresolutions.resourcemanager.utility.APIconstants.USER;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.NAMEKEY;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.PASSWORDKEY;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.ROLE;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.TOKEN;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.USERID;

/**
 * Created By: Shivangi on: 3/8/2017
 * Last modified on: 23/5/2017
 * this activity deals with user login
 * and only inflates when user need to login
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ApiCallHandler.Listener {

    private final String TAG = LoginActivity.class.getSimpleName();
    public static final String IS_TOKEN_EXPIRE_EXTRA = "TokenExpire";
    private static final int PENDING_INTENT_ID = 0;
    private static final int SEC_TO_MILLIS = 1000;
    private static final String TOKEN_PREFIX = "bearer ";
    private static final String KEY = "login";
    private Button mBtnToggleSignupLogout;
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private CheckBox mChkShowPassword;
    private boolean mIsExpire = false;
    private SharedPref mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Check for intent
        checkIntentForExpiry();
        //initialize buttons
        initButtons();
        //fill username and password
        fillUserName();
        mSharedPref = SharedPref.getInstance(this);
    }

    //check if token has expired
    private void checkIntentForExpiry() {
        Intent expiryIntent = getIntent();
        if (expiryIntent.hasExtra(IS_TOKEN_EXPIRE_EXTRA)) {
            mIsExpire = expiryIntent.getExtras().getBoolean(IS_TOKEN_EXPIRE_EXTRA);
        }
    }

    //initialize buttons
    private void initButtons() {
        mBtnToggleSignupLogout = (Button) findViewById(R.id.signup);
        mBtnToggleSignupLogout.setOnClickListener(this);
        findViewById(R.id.forgotPassword).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
        mChkShowPassword = (CheckBox) findViewById(R.id.show_password);
        mChkShowPassword.setOnClickListener(this);
    }

    //if username if it is present in shared preferance
    private void fillUserName() {
        mEdtUsername = (EditText) findViewById(R.id.ed_username);
        if (mIsExpire) {
            mEdtUsername.setInputType(InputType.TYPE_NULL);
            mBtnToggleSignupLogout.setText(getString(R.string.logout));
        }
        mEdtPassword = (EditText) findViewById(R.id.ed_password);
        LoggerUtility.log(TAG, "Variables initialized");
        //check to see if there is any entry in shared preferences then autofill it
        if (mSharedPref!=null && mSharedPref.containsData(NAMEKEY))
            mEdtUsername.setText(mSharedPref.getSharedPreferences(NAMEKEY));
        //set cursor on password field
        mEdtPassword.requestFocus();
        //set long click on password as false
        mEdtPassword.setLongClickable(false);
    }

    //handle action id checkBox is selected
    private void onClickCheckBox() {
        //if small check box is slected then show password text
        if (mChkShowPassword.isChecked()) {
            mEdtPassword.setTransformationMethod(null);
        } else {
            mEdtPassword.setTransformationMethod(new PasswordTransformationMethod());
        }
        mEdtPassword.setSelection(mEdtPassword.getText().length());
    }

    //handle action if login button is clicked
    private void onClickLogin() {
        //method to handle login button
        if (GeneralTextUtils.getTextFromView(mEdtUsername).length() == 0) {
            GeneralTextUtils.requestFocusIfError(mEdtUsername, getString(R.string.error_field_required));
            return;
        }
        if (mEdtPassword.length() == 0) {
            GeneralTextUtils.requestFocusIfError(mEdtPassword, getString(R.string.error_field_required));
            return;
        }
        if (InternetConnections.hasActiveInternetConnection(LoginActivity.this)) {
            LoggerUtility.log(TAG, "INTERNET connection available");
            checkUserCredentialForLogging(GeneralTextUtils.getTextFromView(mEdtUsername), mEdtPassword.getText().toString());
        } else {
            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.no_internet));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.forgotPassword):
                startNewActivity(ForgotPasswordActivity.class);
                break;
            case (R.id.login):
                onClickLogin();
                break;
            case (R.id.signup):
                toggleSignupLogout();
                break;
            case (R.id.show_password):
                onClickCheckBox();
                break;
            default:
                LoggerUtility.log(TAG, "no options matched in onclick");
        }
    }

    //this function toggles signup button between signup and Logout depending whether token is expired
    private void toggleSignupLogout() {
        if (!mIsExpire) {
            Intent intentSignup = new Intent(this, SignUpActivity.class);
            startActivity(intentSignup);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } else {
            mEdtUsername.setInputType(InputType.TYPE_CLASS_TEXT);
            mEdtUsername.setText(null);
            mEdtPassword.setText(null);
            mSharedPref.removeToken(null);
        }
    }

    //call api to check if username and password is matching else display toast
    private void checkUserCredentialForLogging(final String userName, final String passWord) {
        //loginsetter contains username and password
        LoginSetter loginSetter = new LoginSetter();
        loginSetter.setUserName(userName);
        loginSetter.setPassword(passWord);
        ApiCallHandler apiCallHandler = ApiCallHandler.getInstance();
        Call<ResponseGetterWithToken> call = apiCallHandler.getInterface().getResponse(LOGIN_REQUEST_HEADER_VALUE, loginSetter);
        LoggerUtility.log(TAG, "response in call object");
        apiCallHandler.getResponse(call, KEY, this, this);
    }

    //Handle positive callback
    @Override
    public void onSuccessResult(Object result, String key) {
        ResponseGetterWithToken response = (ResponseGetterWithToken) result;
        if (response.getCode() == OK) {
            mSharedPref.setSharePreferences(NAMEKEY, mEdtUsername.getText().toString());
            mSharedPref.setSharePreferences(USERID, response.getID().toString());
            mSharedPref.setSharePreferences(PASSWORDKEY,mEdtPassword.getText().toString());
            mSharedPref.setSharePreferences(TOKEN, TOKEN_PREFIX + response.getToken().getAccessToken());
            mSharedPref.setSharePreferences(ROLE, response.getRole());
            startTokenTimer(response.getToken().getExpiresIn());
            boolean isUser = USER.equals(response.getRole());
            startNewActivity(isUser ? HomeActivity.class : AdminHomeActivity.class);
        } else
            LoggerUtility.toastShort(LoginActivity.this, response.getMessage());
    }

    /**
     * deal with token timer to clear off token on timeout
     * @param time s being modified to convert to miliseconds
     */
    private void startTokenTimer(long time) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        time = time * SEC_TO_MILLIS;//to convert to miliseconds
        Intent tokenIntent = new Intent(LoginActivity.this, AlarmTokenCleanerReceiver.class);
        //trigger pending intent after time elapsed
        PendingIntent pendingIntent = PendingIntent.getBroadcast(LoginActivity.this, PENDING_INTENT_ID, tokenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, pendingIntent);
    }

    //start activity by starting new intent
    private void startNewActivity(Class activity) {
        startActivity(new Intent(this, activity));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }
}



