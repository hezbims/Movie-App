package com.example.finalprojectsekolahbeta1.detailfragment

import android.app.Application
import androidx.lifecycle.*
import com.example.finalprojectsekolahbeta1.database.Movie
import com.example.finalprojectsekolahbeta1.database.Video
import com.example.finalprojectsekolahbeta1.database.VideoResult
import com.example.finalprojectsekolahbeta1.database.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(
    val currentMovie : Movie,
    private val app : Application
    ) : AndroidViewModel(app){
    private val _videos = MutableLiveData<List<Video?>?>(null)
    val videos : LiveData<List<Video?>?>
        get() = _videos
    init{
        makeCallVideos()
    }

    private fun makeCallVideos(){
        api.getVideosFromMovie(currentMovie.id!!).enqueue(object : Callback<VideoResult>{
            override fun onResponse(call: Call<VideoResult>, response: Response<VideoResult>) {
                if (response.isSuccessful)
                    _videos.value = response.body()?.results
            }

            override fun onFailure(call: Call<VideoResult>, t: Throwable) {
            }
        })
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