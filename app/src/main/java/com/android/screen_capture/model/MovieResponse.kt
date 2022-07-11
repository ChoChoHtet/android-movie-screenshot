package com.android.screen_capture.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Response")
    val response:Boolean,

    @SerializedName("Search")
    val movies: List<Movie>?,


    val totalResults:Int
)