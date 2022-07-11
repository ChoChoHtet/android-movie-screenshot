package com.android.screen_capture.repository

import androidx.paging.PagingData
import com.android.screen_capture.model.Movie
import com.android.screen_capture.model.MovieDetail
import com.android.screen_capture.model.MovieResponse
import com.android.screen_capture.utils.Results
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(page:Int):Results<MovieResponse>
    suspend fun getMovieByID(id:String):Results<MovieDetail>

    fun getMovieList():Flow<PagingData<Movie>>
}