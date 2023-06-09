package com.example.moviesample.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesample.data.api.TheMovieDBInterface
import com.example.moviesample.data.model.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsNetworkDataSource(private val apiService: TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState=MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get()=_networkState

    private val _downloadedMovieDetailsResponse=MutableLiveData<MovieDetails>()
    val downloadedMoviewDetailsResponse : LiveData<MovieDetails>
        get()=_downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId:Int){

        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getData(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            it.message?.let { it1 -> Log.e("MovieDetailsDataSource", it1) }
                        }
                    )
            )



        }catch (e:Exception){
            e.message?.let { Log.e("MovieDetailsDataSource", it) }
        }

    }


}
