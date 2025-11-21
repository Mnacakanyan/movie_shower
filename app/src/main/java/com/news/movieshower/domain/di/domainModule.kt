package com.news.movieshower.domain.di

import com.news.movieshower.domain.usecase.GetTrendingMoviesUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetTrendingMoviesUseCase(movieRepository = get()) }
}