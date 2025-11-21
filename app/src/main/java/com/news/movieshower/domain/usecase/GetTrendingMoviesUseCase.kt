package com.news.movieshower.domain.usecase

import com.news.movieshower.core.Result
import com.news.movieshower.domain.model.ResultDomainModel
import com.news.movieshower.domain.repository.MovieRepository

class GetTrendingMoviesUseCase(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(page: Int = 1): Result<List<ResultDomainModel>> {
        return movieRepository.getTrendingMovies(page = page)
    }
}