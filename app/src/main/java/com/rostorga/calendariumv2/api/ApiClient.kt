package com.rostorga.calendariumv2.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor.Chain

object RetrofitClient{
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ApiClient{
    val apiService: ApiService by lazy {
        RetrofitClient.retrofit.create(ApiService::class.java)
    }
}

