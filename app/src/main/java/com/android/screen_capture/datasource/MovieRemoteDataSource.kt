package com.android.screen_capture.datasource

import com.android.screen_capture.model.Movie
import com.android.screen_capture.model.MovieDetail
import com.android.screen_capture.model.MovieResponse
import com.android.screen_capture.utils.Results

interface MovieRemoteDataSource {
   suspend fun getMovieList(page:Int):Results<MovieResponse>
   suspend fun getMovieByID(id:String):Results<MovieDetail>
}