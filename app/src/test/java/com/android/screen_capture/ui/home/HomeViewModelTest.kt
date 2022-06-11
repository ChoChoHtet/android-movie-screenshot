package com.android.screen_capture.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.screen_capture.MainCoroutineRule
import com.android.screen_capture.dummy.testMovieResponse
import com.android.screen_capture.getOrAwaitValue
import com.android.screen_capture.repository.MovieRepository
import com.android.screen_capture.utils.Results
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup(){
        MockKAnnotations.init(this,relaxed = true)
        viewModel = HomeViewModel(movieRepository)
    }

    @Test
    fun `get all movies live data is set`(): Unit = runTest {
        val result = Results.Success(testMovieResponse)
        coEvery { movieRepository.getMovies(1) } returns  result
        viewModel.getMovies(1)
        advanceUntilIdle()
        Assert.assertEquals(viewModel.observeMovieList.getOrAwaitValue(),Results.Success(result.data.movies))
        Assert.assertEquals(viewModel.movieTotal,2)
    }
}