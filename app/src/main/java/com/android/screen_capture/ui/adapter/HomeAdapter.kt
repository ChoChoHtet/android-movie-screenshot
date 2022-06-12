package com.android.screen_capture.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.screen_capture.R
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

    interface ItemClickListener {
        fun onItemClick(id: String)
    }


}

@BindingAdapter("posterUrl")
fun setPosterImage(imageView: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
       //https://img.i-scmp.com/cdn-cgi/image/fit=contain,width=425,format=auto/sites/default/files/styles/768x768/public/images/methode/2018/07/26/bf01d32e-8fcd-11e8-ad1d-4615aa6bc452_1280x720_204951.jpg?itok=lSmaQVob
        imageView.load(url) {
            crossfade(true)
            allowHardware(false)
            placeholder(R.drawable.ic_screenshot)
            listener(
               onError = {_,errorResult ->
                   run {
                       Log.d("coil_error","url:$url")
                       Log.e("coil_error", " ${errorResult.throwable.localizedMessage}")
                   }
               }
            )
        }
    } else {
        imageView.load(R.drawable.ic_movie) {
            size(150,200)
            crossfade(false)
            allowHardware(false)
        }
    }

}



