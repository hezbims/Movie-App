package com.example.finalprojectsekolahbeta1.database

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_movies")
data class MovieFavorite(
    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null
){
    companion object{
        class MovieFavoriteDiffUtil : DiffUtil.ItemCallback<MovieFavorite>(){
            override fun areItemsTheSame(oldItem: MovieFavorite, newItem: MovieFavorite): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieFavorite,
                newItem: MovieFavorite
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}