package com.android.screen_capture.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.screen_capture.databinding.ItemHomeBinding
import com.android.screen_capture.model.Movie

class MovieAdapter(private val itemClickListener: HomeAdapter.ItemClickListener) :
    PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }


    }

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

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }
}