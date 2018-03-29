package com.example.networkmodule.singletonNetWork.retrofit;

import android.app.DownloadManager;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.SimpleXmlConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ddopik on 9/19/2017.
 */

public class RetrofitSingleton {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getXmlClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

//
// Retrofit full Request Example
// ====================================
//    public void loadAnswers() {
//        apiUtils =new ApiUtils();
//        mService = apiUtils.getSOService();
//        mService.getAnswers().enqueue(new Callback<SOAnswersResponse>() {
//            @Override
//            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {
//
//                if (response.isSuccessful()) {
//                    mAdapter.updateAnswers(response.body().getItems());
////                    Log.e("MainActivity", "posts loaded from API" + response.body().getBackoff().toString());
//                } else {
//                    int statusCode = response.code();
//                    // handle request errors depending on status code
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {
//
//                Log.d("MainActivity", "error loading from API");
//
//            }
//        });
//    }
}
