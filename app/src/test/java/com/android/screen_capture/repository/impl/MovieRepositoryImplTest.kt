package com.android.screen_capture.repository.impl

import com.android.screen_capture.datasource.MovieRemoteDataSource
import com.android.screen_capture.dummy.testMovieDetail
import com.android.screen_capture.dummy.testMovieResponse
import com.android.screen_capture.utils.Results
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {

    @MockK
    private lateinit var movieRemoteDataSource: MovieRemoteDataSource

    private lateinit var movieRepository: MovieRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        movieRepository = MovieRepositoryImpl(movieRemoteDataSource)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when getMovies was called return movie list `() = runTest {
        coEvery { movieRemoteDataSource.getMovieList(1) } returns Results.Success(testMovieResponse)
        movieRepository.getMovies(1)
        coVerify { movieRemoteDataSource.getMovieList(1) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when getMovieById was called return movie detail`() = runTest {
        coEvery { movieRemoteDataSource.getMovieByID("tt1") } returns Results.Success(testMovieDetail)
        movieRepository.getMovieByID("tt1")
        coVerify { movieRemoteDataSource.getMovieByID("tt1") }
    }

}