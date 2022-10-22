package com.example.finalprojectsekolahbeta1.detailfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalprojectsekolahbeta1.database.Movie

abstract class NavigateToDetail(protected val app : Application) : AndroidViewModel(app) {
    private val _currentMovie = MutableLiveData<Movie?>(null)
    val currentMovie : LiveData<Movie?>
        get() = _currentMovie

    fun navigateToDetail(movie : Movie){
        _currentMovie.value = movie
    }

    fun doneNavigatingToDetail(){
        _currentMovie.value = null
    }
}