package com.android.screen_capture.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.screen_capture.model.Movie
import com.android.screen_capture.model.MovieDetail
import com.android.screen_capture.repository.MovieRepository
import com.android.screen_capture.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _movie = MutableLiveData<Results<MovieDetail>>()

    val observeMovie: LiveData<Results<MovieDetail>>
        get() = _movie

    fun getMovieByID(id: String) {
        viewModelScope.launch {
            _movie.postValue(Results.Loading)
            movieRepository.getMovieByID(id).let { result ->
                _movie.value = result
            }
        }
    }
}