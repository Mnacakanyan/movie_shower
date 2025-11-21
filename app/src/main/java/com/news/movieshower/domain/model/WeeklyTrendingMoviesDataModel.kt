package com.news.movieshower.domain.model

data class WeeklyTrendingMoviesDataModel(
    val page: Int,
    val results: List<ResultDomainModel>,
    val totalPages: Int,
    val totalResults: Int
)
data class ResultDomainModel(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val mediaType: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)