package com.example.movieapp.data.remote.service

import com.example.movieapp.data.remote.MovieAppService
import com.example.movieapp.model.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImp @Inject constructor(val apiService: MovieAppService): ApiHelper{
    override suspend fun getMovies(apiKey: String?, page: Int): Response<MovieResponse> {
        return apiService.getMovies(apiKey ,page)
    }

}