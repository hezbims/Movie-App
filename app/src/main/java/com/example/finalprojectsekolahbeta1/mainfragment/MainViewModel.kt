package com.example.finalprojectsekolahbeta1.mainfragment

import android.app.Application
import android.widget.Toast
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
                    Toast.makeText(app , "${_popularLoadingStatus.value}" , Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<PageInfo>, t: Throwable) {
                    _popularLoadingStatus.value = LoadingStatus.FAILURE
                    Toast.makeText(app , "${_popularLoadingStatus.value}" , Toast.LENGTH_LONG).show()
                }
            }
        )
    }


    private val _topRatedMovies = MutableLiveData<List<Movie?>?>(null)
    val topRatedMovies : LiveData<List<Movie?>?>
        get() = _topRatedMovies

    init{
        api.getTopRatedMovies().enqueue(object  : Callback<PageInfo>{
            override fun onResponse(call: Call<PageInfo>, response: Response<PageInfo>) {
                if (response.isSuccessful)
                    _topRatedMovies.value = response.body()?.results
            }

            override fun onFailure(call: Call<PageInfo>, t: Throwable) {

            }
        })
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