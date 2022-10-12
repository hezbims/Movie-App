package com.example.finalprojectsekolahbeta1.database

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("popular?api_key=39ae7628fbc44ad94d717632437d96a0")
    fun getLatestMovie() : Call<PageInfo>

    @GET("{movie_id}/videos?api_key=39ae7628fbc44ad94d717632437d96a0&language=en-US")
    fun getVideoFromMovie(@Path("movie_id")id : Int) : Call<VideoResult>
}

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/movie/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val movieApi: MovieApi = retrofit.create(MovieApi::class.java)