package com.example.appcatphotos.API

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCustom {

    val imgurAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor())
                    .build()
            )
            .build()
            .create(imgurAPI::class.java)
    }
}