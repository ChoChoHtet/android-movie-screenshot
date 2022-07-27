package com.android.screen_capture.ui.home

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.screen_capture.model.Movie
import com.android.screen_capture.repository.MovieRepository
import com.android.screen_capture.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _movieList = MutableLiveData<Results<List<Movie>>>()

    val observeMovieList: LiveData<Results<List<Movie>>>
        get() = _movieList


    private val _moviesTotal = ObservableField<Int>()
    val movieTotal: Int
        get() = _moviesTotal.get() ?: 0

    fun getMovies(page: Int) {
        viewModelScope.launch {
            if(page == 1){
                _movieList.value = Results.Loading
            }
            movieRepository.getMovies(page).let { result ->
                when (result) {
                    is Results.Success -> {
                        _moviesTotal.set(result.data.totalResults)
                        _movieList.value = Results.Success(result.data.movies)
                    }
                    is Results.Error -> _movieList.value = Results.Error(result.exception)
                    else -> _movieList.value = Results.Error("General Error")
                }
            }
        }
    }
}