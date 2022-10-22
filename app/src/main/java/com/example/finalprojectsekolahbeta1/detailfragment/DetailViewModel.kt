package com.example.finalprojectsekolahbeta1.detailfragment

import android.app.Application
import androidx.lifecycle.*
import com.example.finalprojectsekolahbeta1.LoadingStatus
import com.example.finalprojectsekolahbeta1.R
import com.example.finalprojectsekolahbeta1.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(
    val currentMovie : Movie,
    private val app : Application
    ) : AndroidViewModel(app){

    private val dao = MovieFavoriteDatabase.getInstance(app).dao

    private val _videos = MutableLiveData<List<Video?>?>(null)
    val videos : LiveData<List<Video?>?>
        get() = _videos
    init{
        makeCallVideos()
    }
    private val _loadingStatus = MutableLiveData(LoadingStatus.LOADING)
    val loadingStatus : LiveData<LoadingStatus>
        get() = _loadingStatus

    private fun makeCallVideos(){
        api.getVideosFromMovie(currentMovie.id!!).enqueue(object : Callback<VideoResult>{
            override fun onResponse(call: Call<VideoResult>, response: Response<VideoResult>) {
                if (response.isSuccessful) {
                    _loadingStatus.value = LoadingStatus.SUCCESS
                    _videos.value = response.body()?.results
                }
                else
                    _loadingStatus.value = LoadingStatus.FAILURE
            }

            override fun onFailure(call: Call<VideoResult>, t: Throwable) {
                _loadingStatus.value = LoadingStatus.FAILURE
            }
        })
    }

    private val currentMovieFavorite =
        with(currentMovie){
            MovieFavorite(
                id = id!!,
                title = title,
                posterPath = posterPath,
                voteCount = voteCount,
                voteAverage = voteAverage
            )
        }

    fun deleteFromFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(currentMovieFavorite)
        }
    }

    fun addToFavorite(){
        viewModelScope.launch(Dispatchers.IO){
            dao.insert(currentMovieFavorite)
        }
    }

    val isFavorite = Transformations.map(dao.getMovieId(currentMovie.id!!)){
        if (it != null)
            R.drawable.ic_baseline_favorite_red
        else
            R.drawable.ic_baseline_favorite_border
    }
}

class DetailViewModelFactory(private val movie : Movie, private val app : Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(DetailViewModel::class.java))
            return DetailViewModel(movie , app) as T
        throw IllegalArgumentException("Ada Bug")
    }
}