package com.android.screen_capture.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("imdbID")
    val id:String?,

    @SerializedName("Title")
    val title: String?,

    @SerializedName("Year")
    val year:String?,

    @SerializedName("Type")
    val type: String?,

    @SerializedName("Poster")
    val poster:String?
) : Parcelable