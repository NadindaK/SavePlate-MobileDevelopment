package com.dicoding.saveplate.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("email")
	val email: String
)
