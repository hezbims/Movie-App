package com.example.finalprojectsekolahbeta1.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieFavoriteDao {
    @Insert
    suspend fun insert(movie : MovieFavorite)

    @Delete
    suspend fun delete(movie : MovieFavorite)

    @Query("""
       SELECT id
       FROM favorite_movies
       where id = :id
    """)
    fun getMovieId(id : Int) : LiveData<Int?>

    @Query("""
        SELECT *
        FROM favorite_movies
    """)
    fun getAllFavoriteMovies() : LiveData<List<MovieFavorite>>
}