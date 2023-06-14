package com.dicoding.saveplate.response

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("confidence")
	val confidence: Any,

	@field:SerializedName("label")
	val label: String
)
