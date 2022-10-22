package com.example.finalprojectsekolahbeta1.genresfragment

import android.app.Application
import androidx.lifecycle.*
import com.example.finalprojectsekolahbeta1.LoadingStatus
import com.example.finalprojectsekolahbeta1.database.Genre
import com.example.finalprojectsekolahbeta1.database.GenreResult
import com.example.finalprojectsekolahbeta1.database.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenresViewModel(private val app : Application) : AndroidViewModel(app) {
    private val _currentGenre = MutableLiveData<Genre?>(null)
    val currentGenre : LiveData<Genre?>
        get() = _currentGenre
    fun navigateToMoviesByGenre(genre : Genre?){_currentGenre.value = genre}
    fun doneNavigateToMoviesByGenre(){_currentGenre.value = null}
    
    private val _genreResult = MutableLiveData<List<Genre?>?>(null)
    val genreResult : LiveData<List<Genre?>?>
        get() = _genreResult
    init{
        makeCallGenreResult()
    }

    private val _loadingStatus = MutableLiveData(LoadingStatus.LOADING)
    val loadingStatus : LiveData<LoadingStatus>
        get() = _loadingStatus

    private fun makeCallGenreResult(){
        api.getGenreList().enqueue(object : Callback<GenreResult> {
            override fun onResponse(call: Call<GenreResult>, response: Response<GenreResult>) {
                if (response.isSuccessful) {
                    _genreResult.value = response.body()?.genres
                    _loadingStatus.value = LoadingStatus.SUCCESS
                }
                else
                    _loadingStatus.value = LoadingStatus.FAILURE
            }

            override fun onFailure(call: Call<GenreResult>, t: Throwable) {
                _loadingStatus.value = LoadingStatus.FAILURE
            }
        })

    }
}

class GenresViewModelFactory(private val app : Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(GenresViewModel::class.java))
            return GenresViewModel(app) as T
        throw IllegalArgumentException("Ada Bug!")
    }
}