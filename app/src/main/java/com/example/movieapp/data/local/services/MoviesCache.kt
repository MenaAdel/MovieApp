package com.example.movieapp.data.local.services

import androidx.paging.PagingSource
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieResponse

interface MoviesCache {
    suspend fun insertMovies(movies: List<Movie>)
    fun getMovies(): PagingSource<Int, Movie>
    fun getMovieDetails(id: Int): List<Movie>?
    fun deleteMovies()
    fun saveTime(time: Int)
    fun getTime(): Int?
}