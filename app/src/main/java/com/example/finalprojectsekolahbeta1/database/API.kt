package com.example.finalprojectsekolahbeta1.database

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val apiKey = "39ae7628fbc44ad94d717632437d96a0"

interface API {
    @GET("movie/popular?api_key=$apiKey")
    fun getPopularMovies() : Call<PageInfo>

    @GET("movie/top_rated?api_key=$apiKey")
    fun getTopRatedMovies() : Call<PageInfo>

    @GET("movie/upcoming?api_key=$apiKey")
    fun getUpcomingMovies() : Call<PageInfo>

    @GET("movie/{movie_id}/videos?api_key=$apiKey&language=en-US")
    fun getVideosFromMovie(@Path("movie_id")id : Int) : Call<VideoResult>

    @GET("genre/movie/list?api_key=$apiKey&language=en-US")
    fun getGenreList() : Call<GenreResult>

    @GET("discover/movie?api_key=$apiKey")
    fun getMoviesByGenre(@Query("with_genres")id : Int) : Call<PageInfo>

}

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api: API = retrofit.create(API::class.java)