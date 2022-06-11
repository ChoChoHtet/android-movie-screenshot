package com.android.screen_capture.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.screen_capture.MainCoroutineRule
import com.android.screen_capture.dummy.testMovieDetail
import com.android.screen_capture.getOrAwaitValue
import com.android.screen_capture.repository.MovieRepository
import com.android.screen_capture.utils.Results
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeDetailViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var movieRepository: MovieRepository

    private lateinit var homeDetailViewModel: HomeDetailViewModel

    @Before
    fun setup(){
        MockKAnnotations.init(this,relaxed = true)
        homeDetailViewModel = HomeDetailViewModel(movieRepository)
    }

    @Test
    fun `when movie by id was called live data was set`(){
        val result = Results.Success(testMovieDetail)
        runTest {
            coEvery { movieRepository.getMovieByID("tt1") } returns result
            homeDetailViewModel.getMovieByID("tt1")
            advanceUntilIdle()
            Assert.assertEquals(homeDetailViewModel.observeMovie.getOrAwaitValue(),result)
        }
    }


}