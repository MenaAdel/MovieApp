package com.example.movieapp.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MovieItemBinding
import com.example.movieapp.extentions.setImage
import com.example.movieapp.model.Movie

class MoviesAdapter(
    val listener: (Int?) -> Unit,
    var movies: List<Movie>
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun updateMoviesList(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class MoviesViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            with(binding) {
                image.setImage(item.posterPath)
                //title.text = item.title
                root.setOnClickListener {
                    listener(item.id)
                }
            }
        }
    }

    override fun getItemCount(): Int = movies.size
}