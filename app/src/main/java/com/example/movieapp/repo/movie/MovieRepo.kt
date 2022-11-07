package com.example.movieapp.repo.movie

import androidx.paging.PagingSource
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieResponse

interface MovieRepo {

    suspend fun getMovies(apiKey: String ,page: Int)
    fun getMovieDetails(id: Int): Movie?
    fun deleteMovies()
    fun saveTime(time: Int)
    fun getTime(): Int?
    fun getLocalMovies(): PagingSource<Int, Movie>
    fun saveLastResponse(response: MovieResponse)
    fun getResponsePage(): Int
    fun getResponseLastPage(): Int
    suspend fun getRemoteMovies(apiKey: String, page: Int): PagingSource<Int, Movie>?
}