package com.dicoding.saveplate.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("data")
	val data: Data
)

data class Data(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("pic")
	val pic: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
