package com.news.movieshower.presentation.movies_list

import com.news.movieshower.presentation.model.MoviePresentationModel

sealed interface MoviesListUiState {
    object Empty : MoviesListUiState
    object Loading : MoviesListUiState
    data class Success(
        val moviesList: List<MoviePresentationModel>,
        val isLoadingNextPage: Boolean = false
    ) : MoviesListUiState
    data class Error(val messageId: Int?) : MoviesListUiState
}
