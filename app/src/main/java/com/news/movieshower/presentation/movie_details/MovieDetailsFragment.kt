package com.news.movieshower.presentation.movie_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.news.movieshower.R
import com.news.movieshower.databinding.FragmentMovieDetailsBinding


class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    private val args: MovieDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        with(binding){
            Glide.with(posterIv)
                .load("https://image.tmdb.org/t/p/w500${args.movieData.posterImageUrl}")
                .into(posterIv)

            titleTv.text = args.movieData.title
            ratingTv.text = getString(R.string.rating,args.movieData.voteAverage)
            releaseDateTv.text = getString(R.string.release_date,args.movieData.releaseDate)
            overviewTv.text = args.movieData.overview
        }
    }


}