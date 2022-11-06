package com.example.movieapp.data.remote

import com.example.movieapp.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAppService {
    @GET("popular")
    suspend fun getMovies(@Query("api_key") apiKey: String? ,@Query("page") page: Int): Response<MovieResponse>
}