package com.rivero.userauth.api

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy

object RetrofitClient {

    // IMPORTANT: 10.0.2.2 is the special IP for Emulator to reach localhost
    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    // Setup Cookie Manager to handle Session ID automatically
    private val cookieManager = CookieManager().apply {
        setCookiePolicy(CookiePolicy.ACCEPT_ALL)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(JavaNetCookieJar(cookieManager)) // Enable Cookies
        .build()

    val instance: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }
}