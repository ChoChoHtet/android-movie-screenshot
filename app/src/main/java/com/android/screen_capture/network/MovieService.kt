package com.android.screen_capture.network

import com.android.screen_capture.model.MovieDetail
import com.android.screen_capture.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET(".")
   suspend fun getMovies(
        @Query("s")title:String,
        @Query("page") page:Int,
        @Query("apikey") apiKey:String
    ):Response<MovieResponse>

   @GET(".")
   suspend fun getMovieByID(
       @Query("i")id:String,
       @Query("apikey") apiKey:String
   ):Response<MovieDetail>

    @GET(".")
    suspend fun getMovieList(
        @Query("s")title:String,
        @Query("page") page:Int,
        @Query("apikey") apiKey:String
    ):MovieResponse
}