package com.example.finalprojectsekolahbeta1.mainfragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalprojectsekolahbeta1.database.Movie
import com.example.finalprojectsekolahbeta1.database.PageInfo
import com.example.finalprojectsekolahbeta1.database.movieApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _currentMovie = MutableLiveData<Movie?>(null)
    val currentMovie : LiveData<Movie?>
        get() = _currentMovie

    fun navigateToDetail(movie : Movie){
        _currentMovie.value = movie
    }

    fun doneNavigatingToDetail(){
        _currentMovie.value = null
    }

    private val _popularMovies : MutableLiveData<List<Movie?>?> = MutableLiveData(null)
    val  popularMovies : LiveData<List<Movie?>?>
        get() = _popularMovies

    init{
        makeCallPopularMovies(movieApi.getLatestMovie())
    }

    private fun makeCallPopularMovies(call : Call<PageInfo>){
        call.enqueue(
            object  : Callback<PageInfo> {
                override fun onResponse(call: Call<PageInfo>, response: Response<PageInfo>) {
                    if (response.isSuccessful)
                        _popularMovies.value = response.body()?.results
                    else
                        Log.d("qqq" , "onResponse fail")
                }

                override fun onFailure(call: Call<PageInfo>, t: Throwable) {
                    Log.d("qqq" , "${t.message}")
                }
            }
        )
    }
}