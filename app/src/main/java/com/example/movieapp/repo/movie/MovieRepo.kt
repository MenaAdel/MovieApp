package com.example.movieapp.repo.movie

import com.example.movieapp.model.Movie

interface MovieRepo {

    suspend fun getMovies(apiKey: String ,page: Int): List<Movie>?
    fun getMovieDetails(id: Int): Movie?
    fun deleteMovies()
    fun saveTime(time: Int)
    fun getTime(): Int?
}