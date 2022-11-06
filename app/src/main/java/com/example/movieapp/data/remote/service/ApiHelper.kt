package com.example.movieapp.data.remote.service

import com.example.movieapp.model.MovieResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getMovies(apiKey: String?, page: Int): Response<MovieResponse>
}