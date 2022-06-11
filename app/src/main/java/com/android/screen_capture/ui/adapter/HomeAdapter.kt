package com.android.screen_capture.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.screen_capture.databinding.ItemHomeBinding
import com.android.screen_capture.model.Movie

class HomeAdapter(
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<HomeAdapter.MovieViewHolder>() {

    private val movieList = mutableListOf<Movie>()

    fun addMovies(movies: List<Movie>) {
        movieList.addAll(movieList.size, movies)
        notifyItemRangeInserted(movieList.size - movies.size, movies.size)
    }

    val movieItemCount: Int
        get() = movieList.size

    inner class MovieViewHolder(
        private val binding: ItemHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movie) {
            binding.apply {
                movie = data
                executePendingBindings()
                itemView.setOnClickListener {
                   itemClickListener.onItemClick(data.id.orEmpty())
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    interface ItemClickListener{
        fun onItemClick(id:String)
    }


}

@BindingAdapter("posterUrl")
fun setPosterImage(imageView: AppCompatImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        imageView.load(url) {
            crossfade(true)
            allowHardware(false)
            crossfade(500)
            // placeholder(R.drawable.ic_launcher_background)
        }
    }

}