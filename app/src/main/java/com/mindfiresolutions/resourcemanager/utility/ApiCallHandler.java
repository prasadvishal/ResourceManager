package com.mindfiresolutions.resourcemanager.utility;

import android.content.Context;
import android.content.res.Resources;

import com.mindfiresolutions.resourcemanager.R;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterBase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mindfiresolutions.resourcemanager.utility.Constants.CLASS_CAST_EXCEPTION;
import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;

/**
 * This interface handles negative callbacks and sends back positive callbacks
 * Created by Shivangi Singh on 5/24/2017.
 */

public class ApiCallHandler {
    private static final String TAG = ApiCallHandler.class.getSimpleName();
    public static final String KEY_GET_USER = "GetUser";
    public static final String KEY_ASSIGN_RESOURCE = "AssignResource";
    public static final String KEY_NEW_LICENSE = "KeyNewLicence";
    public static final String KEY_NEW_BRAND = "KeyNewBrand";
    public static final String KEY_FILL_BRAND = "KeyFillBrand";
    public static final String KEY_FILL_ADMIN = "KeyFillSpinner";
    public static final String KEY_FILL_HARDWARE = "KeyFillHardware";
    public static final String KEY_ADD_SOFTWARE = "KeyAddSoftware";
    public static final String KEY_EDIT_SOFTWARE = "KeyUpdateSpinner";
    public static final String KEY_FILL_LICENCE = "KeyGetAdmin";
    public static final String KEY_GET_RESOURCE = "GetResource";
    public static final String KEY_SET_RESOURCE = "SetResource";
    public static final String KEY_DELETE = "DeleteKey";
    public static final String KEY_HW_SUMMARY = "HardwareSummary";
    public static final String KEY_SOFTWARE_KEY = "SoftwareKey";
    public static final String KEY_SOFTWARE_SUBCAT = "softwareSubcat";
    public static final String KEY_HARDWARE_SUBCAT = "hardwareSubcat";
    public static final String KEY_USER_NEW_REQ = "userNewRequest";
    public static final String KEY_HARDWARE_IMAGE = "hardwareImage";
    public static final String GET_USER_DETAILS = "getUserDetails";

    private static ApiCallHandler sInstance;

    /**
     * private constructor for singleton pattern
     */
    private ApiCallHandler() {/*Ignored*/}

    /**
     * get object of the APIcallHandler type
     *
     * @return an singleton object
     */
    public static ApiCallHandler getInstance() {
        if (sInstance == null)
            sInstance = new ApiCallHandler();
        return sInstance;
    }

    /**
     * Listener which handles positive callbacks and redirects it correponding activity
     */
    public interface Listener {
        void onSuccessResult(Object result, String key);
    }

    /**
     * @return returns a reference to retrofit builder class
     */
    public CallAPIInterface getInterface() {
        return ServiceGenerator.createService(CallAPIInterface.class);
    }

    /**
     * get response using this method after you have send call object,key to identify callback, context of the activity, listener
     *
     * @param call     call object which contains request/response types
     * @param key      key to identify callback
     * @param context  context of the activity
     * @param listener listener object which has implemented this interface
     */
    @SuppressWarnings({"raw type", "unchecked"})
    public void getResponse(Call call, final String key, final Context context, final Listener listener) {
        ProgressDialogUtility.startProgressDialog(context);
        if (InternetConnections.checkInternetConnection(context)) {
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.body() != null && listener != null)
                    try {
                        ProgressDialogUtility.dismissProgressDialog();
                        ResponseGetterBase result = (ResponseGetterBase) response.body();
                        if(result.getCode()== OK)
                        {
                            LoggerUtility.toastShort(context,result.getMessage());
                        }
                        else
                        {
                            LoggerUtility.toastLong(context, result.getMessage());
                        }
                    }
                    catch(java.lang.ClassCastException e)
                    {
                        LoggerUtility.log(CLASS_CAST_EXCEPTION,e.getLocalizedMessage());
                    }
                    finally {
                        listener.onSuccessResult(response.body(), key);
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    ProgressDialogUtility.dismissProgressDialog();
                    LoggerUtility.log(TAG, "Error requesting API");
                    t.printStackTrace();
                }
            });
        } else {
            LoggerUtility.toastShort(context, context.getString(R.string.no_internet));
        }
    }
}


