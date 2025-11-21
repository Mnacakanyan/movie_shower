package com.news.movieshower.data.interceptor

import com.news.movieshower.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${BuildConfig.MOVIE_DB_API_KEY}"
            ).build()

        return chain.proceed(newRequest)
    }
}