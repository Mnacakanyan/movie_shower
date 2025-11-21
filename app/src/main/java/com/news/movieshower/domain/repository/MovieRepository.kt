package com.news.movieshower.domain.repository

import com.news.movieshower.core.Result
import com.news.movieshower.domain.model.ResultDomainModel

interface MovieRepository {

    suspend fun getTrendingMovies(
        page: Int = 1
    ) : Result<List<ResultDomainModel>>

}