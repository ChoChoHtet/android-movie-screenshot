package com.android.screen_capture.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("Title")
    val title:String?,

    @SerializedName("Year")
    val year:String?,

    @SerializedName("Runtime")
    val runTime:String?,

    @SerializedName("Actors")
    val actors:String?,

    @SerializedName("Plot")
    val plot:String? ,

    @SerializedName("Poster")
    val poster:String?


)