package com.dicoding.saveplate.retrofit


import com.dicoding.saveplate.data.LoginPayload
import com.dicoding.saveplate.data.RegisterPayload
import com.dicoding.saveplate.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/signup")
    fun register(
       @Body payload: RegisterPayload
    ) : Call<RegisterResponse>

    @POST("/signin")
    fun login(
        @Body payload: LoginPayload
    ) : Call<LoginResponse>

    @GET("/user")
    fun getProfile(
        @Header("Authorization") token: String,
        @Header("Cookie") jwtToken: String,
    ): Call<ProfileResponse>


}