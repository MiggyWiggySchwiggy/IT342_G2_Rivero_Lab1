package com.rivero.userauth.api

import com.rivero.userauth.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("auth/register")
    fun register(@Body user: User): Call<User>

    @POST("auth/login")
    fun login(@Body user: User): Call<User>

    @POST("auth/logout")
    fun logout(): Call<Void>

    @GET("user/me")
    fun getProfile(): Call<User>
}