package com.example.movieapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("page")
    var page: Int = 0,

    @SerializedName("results")
    var movies: List<Movie>? = null,

    @SerializedName("total_results")
    var totalResults: Int = 0,

    @SerializedName("total_pages")
    var totalPages: Int = 0
) : Parcelable