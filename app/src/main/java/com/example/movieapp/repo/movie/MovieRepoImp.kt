package com.example.movieapp.repo.movie

import android.content.Context
import androidx.paging.PagingSource
import com.example.movieapp.data.local.services.MoviesCache
import com.example.movieapp.data.remote.service.ApiHelper
import com.example.movieapp.model.Movie
import com.example.movieapp.ui.main.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepoImp @Inject constructor(
    private val moviesCache: MoviesCache,
    private val apiHelper: ApiHelper,
    @ApplicationContext val context: Context
): MovieRepo {
    override suspend fun getMovies(apiKey: String ,page: Int): List<Movie>? {
        if (isNetworkAvailable(context)) {
            return if (/*  TODO checkTime()*/ true) {
                getRemoteMovies(apiKey, page)
            } else {
                getLocalMovies()
            }
        } else {
            return getLocalMovies()
        }
    }

    private suspend fun getRemoteMovies(apiKey: String, page: Int): List<Movie>? {
        val response = apiHelper.getMovies(apiKey ,page)
        return if (response.isSuccessful) {
            withContext(Dispatchers.IO) {
                response.body()?.movies?.let { saveMovies(it) }
            }
            getLocalMovies()
        } else {
            getLocalMovies()
        }
    }

    private suspend fun getLocalMovies(): List<Movie>? {
        return moviesCache.getMovies()
    }

    private suspend fun saveMovies(movies: List<Movie>) {
        moviesCache.insertMovies(movies)
    }

    override fun getMovieDetails(id: Int): Movie? {
        return moviesCache.getMovieDetails(id)?.first()
    }

}