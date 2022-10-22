package com.example.finalprojectsekolahbeta1.database

import com.google.gson.annotations.SerializedName

data class GenreResult(

	@field:SerializedName("genres")
	val genres: List<Genre?>? = null
)

