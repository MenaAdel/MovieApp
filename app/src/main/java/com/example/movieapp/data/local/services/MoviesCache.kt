package com.example.movieapp.data.local.services

import com.example.movieapp.model.Movie

interface MoviesCache {
    suspend fun insertMovies(movies: List<Movie>)
    suspend fun getMovies(): List<Movie>?
    fun getMovieDetails(id: Int): List<Movie>?
}