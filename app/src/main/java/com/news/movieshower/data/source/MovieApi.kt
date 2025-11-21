package com.news.movieshower.data.source

import com.news.movieshower.data.model.GenresResponse
import com.news.movieshower.data.model.WeeklyTrendingMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : WeeklyTrendingMoviesResponse

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("language") language: String = "en"
    ) : GenresResponse
}