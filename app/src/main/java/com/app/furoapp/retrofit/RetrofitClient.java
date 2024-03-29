package com.app.furoapp.retrofit;

import android.content.Context;
import android.util.Log;

import com.app.furoapp.BuildConfig;
import com.app.furoapp.activity.HomeMainActivity;
import com.app.furoapp.utils.FuroPrefs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {


    public static ApiInterface getClient() {

        return retrofitBuilder().create(ApiInterface.class);
    }

    public static ApiInterface getClientnew() {
        return retrofitloginBuilder().create(ApiInterface.class);
    }


    public static Gson gson() {
        return new GsonBuilder().setDateFormat("yyyy-M  M-dd'T'HH:mm:ssZ").create();
    }

    private static OkHttpClient okHttp() {
        Context context;

// set your desired log level

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (BuildConfig.DEBUG) {
            return new OkHttpClient().newBuilder()
                    .connectTimeout(2, TimeUnit.HOURS)
                    .readTimeout(2, TimeUnit.HOURS)
                    .writeTimeout(2, TimeUnit.HOURS)
                    /* .addInterceptor(new Interceptor() {
                         @Override
                         public Response intercept(Chain chain) throws IOException {
                             Request original = chain.request();
                            *//* String accessTok = FuroPrefs.getString(context, "accessToken");
                            Log.i("accesstoken",accessTok);
                            Request newRequest = original.newBuilder()
                                    .header("Authentication", accessTok)
                                    .build();*//*


             *//*  return chain.proceed(newRequest);*//*
                        }
                    })*/
                    .addInterceptor(logging)
                    .retryOnConnectionFailure(true) /*added by me*/
                    .build();


        } else {
            return new OkHttpClient().newBuilder()
                    .connectTimeout(2, TimeUnit.HOURS)
                    .readTimeout(2, TimeUnit.HOURS)
                    .writeTimeout(2, TimeUnit.HOURS)
                    .retryOnConnectionFailure(true)
                    .build();
        }


       /* if (BuildConfig.DEBUG) {
            return new OkHttpClient().newBuilder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .retryOnConnectionFailure(true)
                    .build();
        } else {
            return new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
        }*/

    }

    private static Retrofit retrofitBuilder() {
        return new Retrofit.Builder()
                .client(okHttp())
                .baseUrl(BuildConfig.API_SERVER_IP)
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .build();
    }

    private static Retrofit retrofitloginBuilder() {
        return new Retrofit.Builder()
                .client(okHttp())
                .baseUrl(BuildConfig.API_SERVER_IP)
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .build();
    }


}




