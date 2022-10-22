package com.example.finalprojectsekolahbeta1.moviesbygenre

import android.app.Application
import androidx.lifecycle.*
import com.example.finalprojectsekolahbeta1.database.Genre
import com.example.finalprojectsekolahbeta1.database.Movie
import com.example.finalprojectsekolahbeta1.database.PageInfo
import com.example.finalprojectsekolahbeta1.database.api
import com.example.finalprojectsekolahbeta1.detailfragment.NavigateToDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalArgumentException

class MoviesByGenreViewModel(val genre : Genre , app: Application)
    : NavigateToDetail(app) {

    private val _moviesList = MutableLiveData<List<Movie?>?>(null)
    val moviesList : LiveData<List<Movie?>?>
        get() = _moviesList

    init{
        makeCallMovieList()
    }

    private fun makeCallMovieList(){
        api.getMoviesByGenre(genre.id!!).enqueue(object : Callback<PageInfo>{
            override fun onResponse(call: Call<PageInfo>, response: Response<PageInfo>) {
                if (response.isSuccessful)
                    _moviesList.value = response.body()?.results
            }

            override fun onFailure(call: Call<PageInfo>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}

class MoviesByGenreViewModelFactory(private val genre : Genre, private val app : Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MoviesByGenreViewModel::class.java))
            return MoviesByGenreViewModel(genre , app) as T
        throw IllegalArgumentException("Ada bug!")
    }
}

