package com.android.screen_capture.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.screen_capture.data.MoviePagingSource
import com.android.screen_capture.data.NETWORK_PAGE_SIZE
import com.android.screen_capture.datasource.MovieRemoteDataSource
import com.android.screen_capture.model.Movie
import com.android.screen_capture.model.MovieDetail
import com.android.screen_capture.model.MovieResponse
import com.android.screen_capture.network.MovieService
import com.android.screen_capture.repository.MovieRepository
import com.android.screen_capture.utils.Results
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieService: MovieService
) : MovieRepository {
    override suspend fun getMovies(page: Int): Results<MovieResponse> {
        return movieRemoteDataSource.getMovieList(page)
    }

    override suspend fun getMovieByID(id: String): Results<MovieDetail> {
        return movieRemoteDataSource.getMovieByID(id)
    }

    override fun getMovieList(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ), pagingSourceFactory = { MoviePagingSource(movieService) }
        ).flow
    }
}