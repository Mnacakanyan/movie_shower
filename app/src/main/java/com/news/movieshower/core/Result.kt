package com.news.movieshower.core


sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val messageId: Int? = null, val exception: Exception? = null) : Result<T>()

    fun onSuccess(func: (T) -> Unit): Result<T>{
        if (this is Success) {
            func(data)
        }
        return this
    }

    fun onError(func: (Int?, Exception?) -> Unit): Result<T>{
        if (this is Error) {
            func(messageId, exception)
        }
        return this
    }


}