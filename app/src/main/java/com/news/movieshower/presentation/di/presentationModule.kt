package com.news.movieshower.presentation.di

import com.news.movieshower.presentation.movies_list.MoviesListViewModel
import org.koin.core.module.dsl.viewModelOf

import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::MoviesListViewModel)
}