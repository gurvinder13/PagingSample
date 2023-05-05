package com.example.moviesample.data.api

import com.example.moviesample.data.model.LoginResponse
import com.example.moviesample.data.model.MovieDetails
import com.example.moviesample.data.model.MovieResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    // https://api.themoviedb.org/3/movie/popular?api_key=8835e164059f2a901b52381b193852d0&page=1

    //Detail
    //https://api.themoviedb.org/3/movie/547016?api_key=8835e164059f2a901b52381b193852d0

    //BASE_URL
    //https://api.themoviedb.org/3/
    //Login
   // https://www.themoviedb.org/login

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getData(@Path("movie_id") id: Int): Single<MovieDetails>

    @POST("login")
    fun login(request: HashMap<String, Any>):Single<LoginResponse>
}