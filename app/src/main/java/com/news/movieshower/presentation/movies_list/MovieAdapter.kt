package com.news.movieshower.presentation.movies_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.news.movieshower.R
import com.news.movieshower.databinding.ItemMovieBinding
import com.news.movieshower.presentation.model.MoviePresentationModel

class MovieAdapter(
    private val onClickListener: (MoviePresentationModel) -> Unit
) : ListAdapter<MoviePresentationModel, RecyclerView.ViewHolder>(MyDiffCallback) {

    private companion object {
        private const val VIEW_TYPE_MOVIE = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    private var list = listOf<MoviePresentationModel>()
    private var isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_MOVIE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
            ViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size + if (isLoading) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position == list.size) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_MOVIE
        }
    }

    fun showLoading(show: Boolean) {
        if (isLoading == show) return
        isLoading = show
        if (show) {
            notifyItemInserted(list.size)
        } else {
            notifyItemRemoved(list.size)
        }
    }

    fun submitData(list: List<MoviePresentationModel>) {
        this.list = list
        notifyItemRangeChanged(0, list.size)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemMovieBinding.bind(view)


        fun bind(item: MoviePresentationModel) {
            binding.titleTv.text = item.title
            binding.ratingTv.text = item.voteAverage

            Glide.with(binding.movieIv)
                .load("https://image.tmdb.org/t/p/w500${item.imageUrl}")
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(false)
                .transform(CenterCrop(), RoundedCorners(32))
                .into(binding.movieIv)


            binding.root.setOnClickListener {
                onClickListener.invoke(item)
            }
        }
    }

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)
}


private object MyDiffCallback: DiffUtil.ItemCallback<MoviePresentationModel>() {
    override fun areItemsTheSame(
        oldItem: MoviePresentationModel,
        newItem: MoviePresentationModel
    ): Boolean {

        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: MoviePresentationModel,
        newItem: MoviePresentationModel
    ): Boolean {

        return oldItem == newItem
    }
}