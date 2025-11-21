package com.news.movieshower.data.mapper

import com.news.movieshower.data.model.Result
import com.news.movieshower.domain.model.ResultDomainModel


fun Result.toDomain(): ResultDomainModel {
    return ResultDomainModel(
        adult = this.adult,
        backdropPath = this.backdrop_path,
        genreIds = this.genre_ids,
        id = this.id,
        mediaType = this.media_type,
        originalLanguage = this.original_language,
        originalTitle = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.poster_path,
        releaseDate = this.release_date,
        title = this.title,
        video = this.video,
        voteAverage = this.vote_average,
        voteCount = this.vote_count
    )
}