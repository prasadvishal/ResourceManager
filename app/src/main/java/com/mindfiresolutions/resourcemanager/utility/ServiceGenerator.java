package com.mindfiresolutions.resourcemanager.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.BASE_URL;

/**
 * class created to build retrofit client
 * Created by Shivangi Singh on 3/28/2017
 * modified on 30th March
 */

public class ServiceGenerator {
    private static final int TIME_OUT_MILIS = 30;
    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    //build retrofit client
    private static final Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    //create service to set interceptor
    public static <S> S createService(Class<S> serviceClass) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(TIME_OUT_MILIS, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_MILIS, TimeUnit.SECONDS).writeTimeout(TIME_OUT_MILIS, TimeUnit.SECONDS).build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
