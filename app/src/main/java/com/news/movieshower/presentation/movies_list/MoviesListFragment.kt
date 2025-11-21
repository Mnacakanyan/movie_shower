package com.news.movieshower.presentation.movies_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.movieshower.R
import com.news.movieshower.databinding.FragmentMoviewsListBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MoviesListFragment : Fragment() {

    private lateinit var binding: FragmentMoviewsListBinding

    private val viewModel by activityViewModel<MoviesListViewModel>()
    private val movieAdapter = MovieAdapter(
        onClickListener = {
            findNavController().navigate(
                MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(
                    movieData = MovieDisplayableData(
                        posterImageUrl = it.imageUrl,
                        title = it.title,
                        voteAverage = it.voteAverage,
                        releaseDate = it.releaseDate,
                        overview = it.overview,
                    )
                ),
            )
        }
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMoviewsListBinding.inflate(inflater, container, false)

        binding.recyclerView.adapter = movieAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val uiState = viewModel.uiState.value as? MoviesListUiState.Success ?: return

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!uiState.isLoadingNextPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {

                        viewModel.fetchData()
                    }
                }
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is MoviesListUiState.Empty -> {}

                        is MoviesListUiState.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.recyclerView.isVisible = false
                        }

                        is MoviesListUiState.Success -> {
                            binding.progressBar.isVisible = false
                            binding.recyclerView.isVisible = true
                            if (state.moviesList.isEmpty()) {
                                binding.emptyTv.isVisible = true
                                binding.emptyTv.text = getString(R.string.empty_list)
                            }else {
                                movieAdapter.submitData(state.moviesList)
                            }
                            movieAdapter.showLoading(state.isLoadingNextPage)
                        }

                        is MoviesListUiState.Error -> {
                            binding.progressBar.isVisible = false
                            binding.recyclerView.isVisible = false
                            binding.errorTv.isVisible = true
                            binding.errorTv.text = getString(state.messageId ?: 0)
                        }
                    }
                }
            }
        }
    }
}