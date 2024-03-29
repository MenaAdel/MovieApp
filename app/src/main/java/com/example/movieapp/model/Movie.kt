package com.example.movieapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class Movie(
    @SerializedName("poster_path")
    var posterPath: String? = null,

    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("original_title")
    var originalTitle: String? = null,

    @SerializedName("original_language")
    var originalLanguage: String? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @SerializedName("popularity")
    var popularity: Double? = null,

    @SerializedName("vote_count")
    var voteCount: Int? = null,

    @SerializedName("video")
    var video: Boolean? = null,

    @SerializedName("vote_average")
    var voteAverage: Double? = null,

    @SerializedName("overview")
    var overview: String? = null
) : Parcelable