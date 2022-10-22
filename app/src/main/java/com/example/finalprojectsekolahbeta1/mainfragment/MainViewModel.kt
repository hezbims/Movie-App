package com.example.finalprojectsekolahbeta1.mainfragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalprojectsekolahbeta1.LoadingStatus
import com.example.finalprojectsekolahbeta1.database.Movie
import com.example.finalprojectsekolahbeta1.database.PageInfo
import com.example.finalprojectsekolahbeta1.database.api
import com.example.finalprojectsekolahbeta1.detailfragment.NavigateToDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(app: Application) : NavigateToDetail(app) {
    private val _popularMovies = MutableLiveData<List<Movie?>?>(null)
    val  popularMovies : LiveData<List<Movie?>?>
        get() = _popularMovies

    private val _popularLoadingStatus = MutableLiveData(LoadingStatus.LOADING)
    val popularLoadingStatus : LiveData<LoadingStatus>
        get() = _popularLoadingStatus

    init{
        api.getLatestMovies().enqueue(
            object : Callback<PageInfo> {
                override fun onResponse(call: Call<PageInfo>, response: Response<PageInfo>) {
                    if (response.isSuccessful) {
                        _popularMovies.value = response.body()?.results
                        _popularLoadingStatus.value =
                            if (_popularMovies.value?.isNotEmpty() == true)
                                LoadingStatus.SUCCESS
                            else
                                LoadingStatus.NORESULT
                    }
                    else
                        _popularLoadingStatus.value = LoadingStatus.FAILURE
                }

                override fun onFailure(call: Call<PageInfo>, t: Throwable) {
                    _popularLoadingStatus.value = LoadingStatus.FAILURE
                }
            }
        )
    }


    private val _topRatedMovies = MutableLiveData<List<Movie?>?>(null)
    val topRatedMovies : LiveData<List<Movie?>?>
        get() = _topRatedMovies
    private val _topRatedLoadingStatus = MutableLiveData(LoadingStatus.LOADING)
    val topRatedLoadingStatus : LiveData<LoadingStatus>
        get() = _topRatedLoadingStatus

    init{
        api.getTopRatedMovies().enqueue(object  : Callback<PageInfo>{
            override fun onResponse(call: Call<PageInfo>, response: Response<PageInfo>) {
                if (response.isSuccessful) {
                    _topRatedLoadingStatus.value = LoadingStatus.SUCCESS
                    _topRatedMovies.value = response.body()?.results
                }
                else
                    _topRatedLoadingStatus.value = LoadingStatus.FAILURE
            }

            override fun onFailure(call: Call<PageInfo>, t: Throwable) {
                _topRatedLoadingStatus.value = LoadingStatus.FAILURE
            }
        })
    }

    val upComingMovies = LinearRecyclerViewData(api.getUpcomingMovies())

    class LinearRecyclerViewData(private val call : Call<PageInfo>){
        private val _loadingStatus = MutableLiveData(LoadingStatus.LOADING)
        val loadingStatus : LiveData<LoadingStatus>
            get() = _loadingStatus

        private val _movies = MutableLiveData<List<Movie?>?>(null)
        val movies : LiveData<List<Movie?>?>
            get() = _movies

        init{
            call.enqueue(object : Callback<PageInfo>{
                override fun onResponse(call: Call<PageInfo>, response: Response<PageInfo>) {
                    if (response.isSuccessful){
                        _loadingStatus.value = LoadingStatus.SUCCESS
                        _movies.value = response.body()?.results
                    }
                    else
                        _loadingStatus.value = LoadingStatus.FAILURE
                }

                override fun onFailure(call: Call<PageInfo>, t: Throwable) {
                    _loadingStatus.value = LoadingStatus.FAILURE
                }
            })
        }
    }
}

class MainViewModelFactory(private val app : Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(app) as T
        throw IllegalArgumentException("Ada bug!")
    }
}