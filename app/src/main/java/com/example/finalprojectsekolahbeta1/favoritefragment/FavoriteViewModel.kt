package com.example.finalprojectsekolahbeta1.favoritefragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalprojectsekolahbeta1.database.MovieFavoriteDatabase
import java.lang.IllegalArgumentException

class FavoriteViewModel(app : Application) : AndroidViewModel(app) {
    private val dao = MovieFavoriteDatabase.getInstance(app).dao

    val movies = dao.getAllFavoriteMovies()
}

class FavoriteViewModelFactory(private val app : Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java))
            return FavoriteViewModel(app) as T
        throw IllegalArgumentException("Ada bug")
    }
}