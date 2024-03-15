package com.msedith.flixsterplus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msedith.flixsterplus.databinding.MovieItemBinding

class MovieAdapter(private var movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder) {
            with(movies[position]) {
                binding.tvTitle.text = title
                binding.tvOverview.text = overview

                Glide.with(holder.itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${movies[position].poster_path}")
                    .placeholder(R.drawable.loading_placeholder)
                    .error(R.drawable.loading_error)
                    .into(holder.binding.ivPoster)
            }
        }
    }

    fun updateMovies(newMovies: List<Movie>) {
        this.movies = newMovies
        notifyDataSetChanged()
    }

    override fun getItemCount() = movies.size
}
