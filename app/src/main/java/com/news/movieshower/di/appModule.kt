package com.news.movieshower.di

import com.news.movieshower.data.di.dataModule
import com.news.movieshower.domain.di.domainModule
import com.news.movieshower.presentation.di.presentationModule

val appModule = dataModule + domainModule + presentationModule