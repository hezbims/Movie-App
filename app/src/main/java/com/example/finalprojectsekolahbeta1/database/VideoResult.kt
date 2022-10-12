package com.example.finalprojectsekolahbeta1.database

import com.google.gson.annotations.SerializedName

data class VideoResult(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("results")
	val results: List<Video?>? = null
)