package com.dicoding.saveplate.retrofit


import com.dicoding.saveplate.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/signup")
    fun register(
        @Field("name") name :  String,
        @Field("email") email :  String,
        @Field("password") password :  String,
        @Field("confPassword") confPassword :  String
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("/signin")
    fun login(
        @Field("email") email :  String,
        @Field("password") password :  String
    ) : Call<LoginResponse>
}