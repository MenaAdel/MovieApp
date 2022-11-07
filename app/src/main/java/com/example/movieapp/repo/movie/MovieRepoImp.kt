package com.example.movieapp.repo.movie

import android.content.Context
import androidx.paging.PagingSource
import com.example.movieapp.LAST_PAGE
import com.example.movieapp.PAGE
import com.example.movieapp.TIME_TO_UPDATE
import com.example.movieapp.data.local.preference.Preferences
import com.example.movieapp.data.local.services.MoviesCache
import com.example.movieapp.data.remote.service.ApiHelper
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieResponse
import com.example.movieapp.ui.getCurrentTime
import com.example.movieapp.ui.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.abs

class MovieRepoImp @Inject constructor(
    private val moviesCache: MoviesCache,
    private val apiHelper: ApiHelper,
    private val preference: Preferences,
    @ApplicationContext val context: Context
) : MovieRepo {
    override suspend fun getMovies(apiKey: String, page: Int) {
         if (isNetworkAvailable(context)) {
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
        abs(((savedTime ?: 0) - currentTime)) >= TIME_TO_UPDATE

    override suspend fun getRemoteMovies(apiKey: String, page: Int): PagingSource<Int, Movie>? {
        val response = apiHelper.getMovies(apiKey, page)
        return if (response.isSuccessful) {
            withContext(Dispatchers.IO) {
                response.body()?.movies?.let { saveMovies(it) }
                response.body()?.let {
                    saveLastResponse(it)
                }
            }
            getLocalMovies()
        } else {
            getLocalMovies()
        }
    }

    override fun getLocalMovies(): PagingSource<Int, Movie> {
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

    override fun saveLastResponse(response: MovieResponse) {
        preference.apply {
            saveIntValue(PAGE ,response.page)
            saveIntValue(LAST_PAGE ,response.totalPages)
        }
    }

    override fun getResponsePage(): Int {
        return preference.getIntValue(PAGE)
    }

    override fun getResponseLastPage(): Int {
        return preference.getIntValue(LAST_PAGE)
    }

}