package com.news.movieshower.presentation.movies_list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDisplayableData(
    val posterImageUrl: String,
    val title: String,
    val voteAverage: String,
    val releaseDate: String,
    val overview: String,
): Parcelable
