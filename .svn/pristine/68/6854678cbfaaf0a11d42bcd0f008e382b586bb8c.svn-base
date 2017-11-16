package com.mindfiresolutions.resourcemanager.commonAdminUser;

/**
 * Created By: Shivangi on: 3/16/2017
 * Last modified on: 4/4/2017
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.ContactSetter;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterBase;
import com.mindfiresolutions.resourcemanager.model.UserNameSetter;
import com.mindfiresolutions.resourcemanager.model.UserResponse;
import com.mindfiresolutions.resourcemanager.utility.ApiCallHandler;
import com.mindfiresolutions.resourcemanager.utility.CallAPIInterface;
import com.mindfiresolutions.resourcemanager.utility.CheckForExpiry;
import com.mindfiresolutions.resourcemanager.utility.GeneralTextUtils;
import com.mindfiresolutions.resourcemanager.utility.InternetConnections;
import com.mindfiresolutions.resourcemanager.utility.LoggerUtility;
import com.mindfiresolutions.resourcemanager.utility.ServiceGenerator;
import com.mindfiresolutions.resourcemanager.utility.SharedPref;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_VALUE;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.BAD_REQUEST;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;
import static com.mindfiresolutions.resourcemanager.utility.SharedPref.USERID;


public class EditUserProfile extends AppCompatActivity implements View.OnClickListener, ApiCallHandler.Listener {

    private EditText mEditConfUserPassword, mEdtMobile;
    private TextView mEmployeeId, mEmailId, mUserName, mFName, mLName;
    private ImageView mUserImg;
    private final int SELECT_PHOTO = 1;
    private final String TAG = EditUserProfile.class.getSimpleName();
    private SharedPref mSharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mSharedPref = SharedPref.getInstance(this);
        getUserProfile();
        //check for Actionbar if present then set tittle
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        if (ab != null && ab.isShowing()) {
            ab.setTitle("Resource Manager");
            ab.setSubtitle("Edit Profile");
            ab.setDisplayHomeAsUpEnabled(false);
        }
        //variables initialized
        mEdtMobile = (EditText) findViewById(R.id.edit_contact);
        mEditConfUserPassword = (EditText) findViewById(R.id.conf_edit_password);
        mUserImg = (ImageView) findViewById(R.id.devInfo_device_image);
        //so that paste option is disabled
        findViewById(R.id.edit_save).setOnClickListener(this);
        mUserImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.edit_save):
                caseSave();
                break;

            case (R.id.devInfo_device_image):
                caseUploadImage();
                break;

            default:
                LoggerUtility.log(TAG, "no cases matched");
                break;
        }
    }

    private void caseSave() {
        if (!GeneralTextUtils.getTextFromView(mEdtMobile).isEmpty()) {
            if ((GeneralTextUtils.getTextFromView(mEdtMobile).length()) != 10) {
                GeneralTextUtils.requestFocusIfError(mEdtMobile, getString(R.string.error_invalid_mobile));
                return;
            }
        }
        //check if password is empty then set error msg
        if (mEditConfUserPassword.getText().toString().isEmpty()) {
            GeneralTextUtils.requestFocusIfError(mEditConfUserPassword, getString(R.string.error_field_required));
            return;
        }
        final String passwordInSharedPreference = mSharedPref.getSharedPreferences(SharedPref.PASSWORDKEY);
        if (passwordInSharedPreference.equals(mEditConfUserPassword.getText().toString())) {
            ContactSetter contactSetter = new ContactSetter();
            contactSetter.setmContact(mEdtMobile.getText().toString());
            if (InternetConnections.checkInternetConnection(EditUserProfile.this)) {
                LoggerUtility.log(TAG, "INTERNET connection available");
                updateUserProfile(contactSetter);
            } else {
                LoggerUtility.toastShort(getApplicationContext(), getString(R.string.no_internet));
            }
        } else {
            LoggerUtility.toastShort(getApplicationContext(), getString(R.string.error_incorrect_password));
        }
    }

    private void caseUploadImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onSuccessResult(Object result, String key) {

    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        String returnStr;
        if (cursor == null) {
            returnStr = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            returnStr = cursor.getString(idx);
            cursor.close();
        }
        return returnStr;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        String filePath = getRealPathFromURIPath(imageUri, EditUserProfile.this);
                        uploadPhoto(filePath);
                        mUserImg.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    private void uploadPhoto(String filePath) {
        MediaType textPlain = MediaType.parse("text/plain");
        String UserID = SharedPref.getInstance(this).getSharedPreferences(USERID);
        RequestBody userIdPart = RequestBody.create(textPlain, UserID);

        File file = new File(filePath);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        // RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        final String token = CheckForExpiry.checkForToken(this);

        final ProgressDialog progressDialog = new ProgressDialog(EditUserProfile.this);
        progressDialog.setMessage(getString(R.string.loading));     //done
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        CallAPIInterface callAPIInterface = ServiceGenerator.createService(CallAPIInterface.class);
        LoggerUtility.log(TAG, "Retrofit object created");
        Call<ResponseGetterBase> call = callAPIInterface.uploadUserImage(token, fileToUpload, userIdPart);
        LoggerUtility.log(TAG, "response in call object");
        call.enqueue(new Callback<ResponseGetterBase>() {
            @Override
            public void onResponse(Call<ResponseGetterBase> call, Response<ResponseGetterBase> response) {
                //response's body
                LoggerUtility.log(TAG, "Response body");
                   try {
                       if(response.body().getCode()==OK)
                       {
                           progressDialog.dismiss();
                           LoggerUtility.toastLong(EditUserProfile.this, response.body().getMessage());
                       }
                       else
                       {
                           progressDialog.dismiss();
                           LoggerUtility.toastLong(EditUserProfile.this, response.body().getMessage());
                       }

                } catch (Exception e) {
                    LoggerUtility.toastLong(EditUserProfile.this, getString(R.string.server_error));
                       progressDialog.dismiss();
                       LoggerUtility.toastLong(EditUserProfile.this, response.body().getMessage());
                               }
            }

            @Override
            public void onFailure(Call<ResponseGetterBase> call, Throwable t) {

                progressDialog.dismiss();
                LoggerUtility.toastShort(EditUserProfile.this,getString(R.string.server_error) );
            }
        });
    }


    void getUserProfile() {
        mUserName = (TextView) findViewById(R.id.username);
        mEmailId = (TextView) findViewById(R.id.email_id);
        mEmployeeId = (TextView) findViewById(R.id.employee_id);
        mFName = (TextView) findViewById(R.id.edit_fname);
        mLName = (TextView) findViewById(R.id.edit_lname);

        final ProgressDialog progressDialog = new ProgressDialog(EditUserProfile.this);
        progressDialog.setMessage(getString(R.string.loading));     //done
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        final String token = mSharedPref.getSharedPreferences(SharedPref.TOKEN);
        CallAPIInterface callAPIInterface = ServiceGenerator.createService(CallAPIInterface.class);
        LoggerUtility.log(TAG, "Retrofit object created");
        UserNameSetter userNameSetter = new UserNameSetter();

        final String userName = mSharedPref.getSharedPreferences(SharedPref.NAMEKEY);

        userNameSetter.setUserName(userName);
        Call<UserResponse> call = callAPIInterface.getProfile(LOGIN_REQUEST_HEADER_VALUE, token, userNameSetter);
        LoggerUtility.log(TAG, "response in call object");
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                //response's body
                progressDialog.dismiss();
                LoggerUtility.log(TAG, "Response body");
                   try {
                //TODO if no body then handle it
                //TODO showing nothing selected log
                if (response.body().getResponse().getCode() == OK) {
                    mUserName.setText(userName);
                    mEmployeeId.setText(response.body().getUserProfile().getEmployeeID());
                    mEmailId.setText(response.body().getUserProfile().getEmail());
                    mFName.setText(response.body().getUserProfile().getFirstName());
                    LoggerUtility.log(TAG, response.body().getUserProfile().getEmployeeID());
                    mLName.setText(response.body().getUserProfile().getLastName());
                    getImage(response.body().getUserProfile().getImageUrl());
                } else {
                    LoggerUtility.toastShort(getApplicationContext(), response.body().getResponse().getMessage());
                }
                } catch (Exception e) {
                   LoggerUtility.toastLong(getApplicationContext(), getString(R.string.error_requesting_api));
                               }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
                LoggerUtility.log(TAG, "Error requesting API");
            }
        });
    }

    private void getImage(String imageUrl) {

        Picasso.with(this).load("http://203.92.41.80:9093/" + imageUrl)//TODO trim base url
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.no_image)      // optional
                .fit()
                .into(mUserImg, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        LoggerUtility.toastLong(EditUserProfile.this, getString(R.string.error_unable_to_fetch_image));
                    }
                });

    }

    void updateUserProfile(ContactSetter contactSetter) {
        CallAPIInterface callAPIInterface = ServiceGenerator.createService(CallAPIInterface.class);
        LoggerUtility.log(TAG, "Retrofit object created in updateUserProfile");
        final String token = mSharedPref.getSharedPreferences(SharedPref.TOKEN);
        Call<ResponseGetterBase> call = callAPIInterface.upateProfile(LOGIN_REQUEST_HEADER_VALUE, token,
                mSharedPref.getSharedPreferences(SharedPref.NAMEKEY), contactSetter);
        LoggerUtility.log(TAG, "response in call object in UpdateUserProfile");

        call.enqueue(new Callback<ResponseGetterBase>() {
            @Override
            public void onResponse(Call<ResponseGetterBase> call, Response<ResponseGetterBase> response) {
                //response's body
                try {
                    LoggerUtility.log(TAG, "Response body");
                    if (response.body().getCode() == OK) {
                        LoggerUtility.log(TAG, "User records Updated sucessfully");
                        //TODO string //done
                        LoggerUtility.toastShort(getApplicationContext(), getString(R.string.user_record_updated));
                    }
                    if (response.body().getCode() == BAD_REQUEST) {
                        LoggerUtility.log(TAG, "User not Updated sucessfully");
                        LoggerUtility.toastShort(getApplicationContext(), getString(R.string.server_error));
                    }
                } catch (Exception e) {
                    LoggerUtility.toastLong(getApplicationContext(), getString(R.string.error_requesting_api));
                }
            }

            @Override
            public void onFailure(Call<ResponseGetterBase> call, Throwable t) {
                LoggerUtility.log(TAG, "Error requesting API");
            }
        });
    }

}