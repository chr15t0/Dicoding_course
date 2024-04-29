package com.dicoding.picodiploma.mycamera.data.api

import com.google.gson.annotations.SerializedName

data class FileUploadResponse(

	@SerializedName("data")
	var data: Data = Data(),

	@SerializedName("message")
	var message: String? = null
)

data class Data(

	@SerializedName("result")
	var result: String? = null,

	@SerializedName("createdAt")
	var createdAt: String? = null,

	@SerializedName("confidenceScore")
	var confidenceScore: Double? = null,

	@SerializedName("isAboveThreshold")
	var isAboveThreshold: Boolean? = null,

	@SerializedName("id")
	var id: String? = null
)
