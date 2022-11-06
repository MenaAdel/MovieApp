package com.example.movieapp.repo.movie

import android.content.Context
import com.example.movieapp.TIME_TO_UPDATE
import com.example.movieapp.data.local.services.MoviesCache
import com.example.movieapp.data.remote.service.ApiHelper
import com.example.movieapp.model.Movie
import com.example.movieapp.ui.getCurrentTime
import com.example.movieapp.ui.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepoImp @Inject constructor(
    private val moviesCache: MoviesCache,
    private val apiHelper: ApiHelper,
    @ApplicationContext val context: Context
) : MovieRepo {
    override suspend fun getMovies(apiKey: String, page: Int): List<Movie>? {
        return if (isNetworkAvailable(context)) {
            if (checkTimeToUpdate()) {
                getRemoteMovies(apiKey, page)
            } else {
                getLocalMovies()
            }
        } else {
            getLocalMovies()
        }
    }

    private fun checkTimeToUpdate(currentTime: Int = getCurrentTime()): Boolean {
        val savedTime = getTime()
        return if (canUpdateData(savedTime, currentTime)) {
            deleteMovies()
            true
        } else {
            if (savedTime == null || savedTime == 0) {
                saveTime(currentTime)
                true
            } else {
                false
            }
        }
    }

    private fun canUpdateData(savedTime: Int?, currentTime: Int) =
        ((savedTime ?: 0) - currentTime) >= TIME_TO_UPDATE

    private suspend fun getRemoteMovies(apiKey: String, page: Int): List<Movie>? {
        val response = apiHelper.getMovies(apiKey, page)
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

    override fun deleteMovies() {
        moviesCache.deleteMovies()
    }

    override fun saveTime(time: Int) {
        moviesCache.saveTime(time)
    }

    override fun getTime(): Int? {
        return moviesCache.getTime()
    }

}