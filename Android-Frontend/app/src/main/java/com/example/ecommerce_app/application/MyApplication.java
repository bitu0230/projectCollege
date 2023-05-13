package com.example.ecommerce_app.application;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.ecommerce_app.constants.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyApplication extends Application {

    public Retrofit retrofitProduct;
    public Retrofit retrofitMerchant;

    public Retrofit retrofitCart;

    public Retrofit retrofitUser;

    public static String userEmail;

    public static void setUserEmail(String userEmail) {
        MyApplication.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }


    @Override
    public void onCreate()
    {


        super.onCreate();
        SharedPreferences sharedPreferences=getSharedPreferences("cache",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
//                .callTimeout(2, TimeUnit.MINUTES)
//                .connectTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS);

           userEmail=sharedPreferences.getString("email",null);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofitProduct=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL_PRODUCT)
                .client(new OkHttpClient())
                .build();

        retrofitMerchant=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL_MERCHANT)
                .client(new OkHttpClient())
                .build();

        retrofitCart=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.BASE_URL_CART)
                .client(new OkHttpClient())
                .build();

        retrofitUser=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.BASE_URL_USER)
                .client(new OkHttpClient())
                .build();
    }


}
