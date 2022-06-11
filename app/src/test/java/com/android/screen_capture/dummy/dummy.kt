package com.android.screen_capture.dummy

import com.android.screen_capture.model.Movie
import com.android.screen_capture.model.MovieDetail
import com.android.screen_capture.model.MovieResponse

val testMovieResponse
    get() = MovieResponse(
        true,
        listOf(
            Movie(
                "1", "movie title", "year", "type", "poster"
            )
        ),
        totalResults = 2
    )

val testMovieDetail
    get() = MovieDetail(
        "title", "year", "1:00 ", "John", "hello plot", "http://poster"
    )