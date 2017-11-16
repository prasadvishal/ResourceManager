package com.mindfiresolutions.resourcemanager.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static com.mindfiresolutions.resourcemanager.utility.HttpCodes.OK;

/**
 * This class checks internet connection and returns true or false depending connection state.
 * Created by Shivangi Singh on 4/2/2017
 * modified on 31st May
 */

public class InternetConnections {
    private static final String TAG = InternetConnections.class.getSimpleName();
    private static final int TIME_OUT_MILIS = 1500;
    private static final String SAMPLE_URL = "http://www.google.com";
    private static final String SET_REQUEST_KEY1 = "User-Agent";
    private static final String SET_REQUEST_KEY2 = "Connection";
    private static final String SET_REQUEST_VALUE1 = "Test";
    private static final String SET_REQUEST_VALUE2 = "close";

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        LoggerUtility.log(TAG, "network connection checked");
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private static class ActiveInternetConnection extends AsyncTask<Context, Boolean, Boolean> {
        @Override
        protected Boolean doInBackground(Context... params) {
            if (checkInternetConnection(params[0])) {
                try {
                    HttpURLConnection urlc = (HttpURLConnection) (new URL(SAMPLE_URL).openConnection());
                    urlc.setRequestProperty(SET_REQUEST_KEY1, SET_REQUEST_VALUE1);
                    urlc.setRequestProperty(SET_REQUEST_KEY2, SET_REQUEST_VALUE2);
                    urlc.setConnectTimeout(TIME_OUT_MILIS);
                    urlc.connect();
                    return (urlc.getResponseCode() == OK);
                } catch (IOException e) {
                    LoggerUtility.log(TAG, "Error checking internet connection" + e);
                }
            } else {
                LoggerUtility.log(TAG, "No network available!");
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        }
    }

    public static boolean hasActiveInternetConnection(Context context) {
        try {
            return new ActiveInternetConnection().execute(context).get();
        } catch (InterruptedException interrupted) {
            LoggerUtility.log(TAG, "interrupted Execution exception");
            return false;
        }
        catch(ExecutionException executionException){
            LoggerUtility.log(TAG,"Concurrent execution exception");
            return false;
        }
    }
}
