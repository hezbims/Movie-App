package com.example.finalprojectsekolahbeta1.database

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable{
	companion object{
		class GenreDiffCallback : DiffUtil.ItemCallback<Genre>(){
			override fun areItemsTheSame(oldItem: Genre, newItem: Genre) =
				oldItem.id == newItem.id

			override fun areContentsTheSame(oldItem: Genre, newItem: Genre) =
				oldItem == newItem
		}
	}
}