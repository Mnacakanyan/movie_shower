package com.news.movieshower.data.di

import com.news.movieshower.data.interceptor.AuthInterceptor
import com.news.movieshower.data.repository.MovieRepositoryImpl
import com.news.movieshower.data.source.MovieApi
import com.news.movieshower.domain.repository.MovieRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { createGsonConverter() }

    single { createRetrofit(converterFactory = get(), client = get()) }

    single { createHttpClient(interceptor = createInterceptor()) }

    single { createMovieApi(retrofit = get()) }

    single<MovieRepository> { createMovieRepository(movieApi = get()) }
}


private fun createRetrofit(
    converterFactory: GsonConverterFactory,
    client: OkHttpClient,
): Retrofit =
    Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

private fun createGsonConverter() = GsonConverterFactory.create()

private fun createHttpClient(
    interceptor: AuthInterceptor
) = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build()


private fun createInterceptor() = AuthInterceptor()

private fun createMovieApi(
    retrofit: Retrofit
): MovieApi =
    retrofit.create(MovieApi::class.java)


private fun createMovieRepository(
    movieApi: MovieApi
) : MovieRepository = MovieRepositoryImpl(movieApi)