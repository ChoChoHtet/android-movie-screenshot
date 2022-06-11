package com.android.screen_capture.repository.impl

import com.android.screen_capture.datasource.MovieRemoteDataSource
import com.android.screen_capture.model.MovieDetail
import com.android.screen_capture.model.MovieResponse
import com.android.screen_capture.repository.MovieRepository
import com.android.screen_capture.utils.Results
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource
) :MovieRepository {
    override suspend fun getMovies(page: Int): Results<MovieResponse> {
        return  movieRemoteDataSource.getMovieList(page)
    }

    override suspend fun getMovieByID(id: String): Results<MovieDetail> {
        return movieRemoteDataSource.getMovieByID(id)
    }
}