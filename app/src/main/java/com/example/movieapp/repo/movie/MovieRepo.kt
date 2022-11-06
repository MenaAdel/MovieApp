package com.example.movieapp.repo.movie

import com.example.movieapp.model.Movie

interface MovieRepo {

    suspend fun getMovies(apiKey: String ,page: Int): List<Movie>?
}