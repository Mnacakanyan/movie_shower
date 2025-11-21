package com.news.movieshower.presentation.mapper

import com.news.movieshower.domain.model.ResultDomainModel
import com.news.movieshower.presentation.model.MoviePresentationModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ResultDomainModel.toPresentation() = MoviePresentationModel(
    imageUrl = this.posterPath,
    title = this.title,
    voteAverage = String.format(Locale.getDefault(),"%.1f", this.voteAverage),
    releaseDate = formatDate(this.releaseDate),
    overview = this.overview
)


private fun formatDate(date: String): String {

    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val localDate = LocalDate.parse(date, inputFormatter)

    return localDate.format(outputFormatter)
}
