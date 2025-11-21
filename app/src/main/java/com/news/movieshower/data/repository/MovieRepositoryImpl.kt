package com.news.movieshower.data.repository


import android.net.http.HttpException
import com.news.movieshower.R
import com.news.movieshower.data.mapper.toDomain
import com.news.movieshower.data.source.MovieApi
import com.news.movieshower.domain.model.ResultDomainModel
import com.news.movieshower.domain.repository.MovieRepository
import java.io.IOException
import com.news.movieshower.core.Result

class MovieRepositoryImpl(
    private val movieApi: MovieApi
): MovieRepository {

    override suspend fun getTrendingMovies(
        page: Int
    ): Result<List<ResultDomainModel>> {

        return try {

            Result.Success(movieApi.getTrendingMovies(page = page).results.map { it.toDomain() } )
        } catch (e: HttpException) {
            Result.Error(messageId = R.string.error_http_exception, exception = e)
        } catch (e: IOException) {
            Result.Error(messageId = R.string.no_internet_connection, exception = e)
        } catch (e: Exception){
            Result.Error(messageId = R.string.error_unknown, exception = e)
        }
    }


}