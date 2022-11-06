package com.example.movieapp.data.local.services

import com.example.movieapp.data.local.dao.MovieAppDao
import com.example.movieapp.model.Movie
import javax.inject.Inject

class MoviesCacheImp @Inject constructor(private val dao: MovieAppDao) : MoviesCache {
    override suspend fun insertMovies(movies: List<Movie>) {
        dao.insertMovie(movies)
    }

    override suspend fun getMovies(): List<Movie> {
        return dao.getMovies()
    }

    override fun getMovieDetails(id: Int): List<Movie> {
        return dao.getMovieDetails(id)
    }
}