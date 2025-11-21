package com.news.movieshower.presentation.movies_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.movieshower.domain.usecase.GetTrendingMoviesUseCase
import com.news.movieshower.presentation.mapper.toPresentation
import com.news.movieshower.presentation.model.MoviePresentationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<MoviesListUiState> =
        MutableStateFlow(MoviesListUiState.Empty)

    val uiState: StateFlow<MoviesListUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MoviesListUiState.Empty
    )

    private var currentPage = 1
    private var isFetching = false
    private var moviesList = mutableListOf<MoviePresentationModel>()

    init {
        fetchData()
    }

    fun fetchData() {
        if (isFetching) return

        viewModelScope.launch {
            isFetching = true
            val currentState = _uiState.value
            if (currentPage == 1) {
                _uiState.value = MoviesListUiState.Loading
            } else if (currentState is MoviesListUiState.Success) {

                _uiState.value = currentState.copy(isLoadingNextPage = true)
            }

            getTrendingMoviesUseCase(currentPage).onSuccess { newData ->
                val newMovies = newData.map { it.toPresentation() }
                moviesList.addAll(newMovies)
                _uiState.value = MoviesListUiState.Success(
                    moviesList = moviesList,
                    isLoadingNextPage = false
                )
                currentPage++
            }.onError { id, _ ->
                _uiState.value = MoviesListUiState.Error(id)
            }

            isFetching = false
        }
    }
}


