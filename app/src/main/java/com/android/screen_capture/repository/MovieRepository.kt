package com.android.screen_capture.repository

import com.android.screen_capture.model.MovieDetail
import com.android.screen_capture.model.MovieResponse
import com.android.screen_capture.utils.Results

interface MovieRepository {
    suspend fun getMovies(page:Int):Results<MovieResponse>
    suspend fun getMovieByID(id:String):Results<MovieDetail>
}