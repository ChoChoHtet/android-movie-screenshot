package com.android.screen_capture.datasource.impl

import com.android.screen_capture.datasource.MovieRemoteDataSource
import com.android.screen_capture.datasource.getResult
import com.android.screen_capture.model.Movie
import com.android.screen_capture.model.MovieDetail
import com.android.screen_capture.network.MovieService
import com.android.screen_capture.utils.API_KEY
import com.android.screen_capture.utils.Results
import com.android.screen_capture.utils.searchTitle
import javax.inject.Inject

class MovieRemoteDataSourceImpl  @Inject constructor(
    private val movieService: MovieService
): MovieRemoteDataSource {
    override suspend fun getMovieList(page: Int) = getResult {
        movieService.getMovies(searchTitle,page, API_KEY)
    }

    override suspend fun getMovieByID(id: String) = getResult {
        movieService.getMovieByID(id, API_KEY)
    }
}