package com.example.movieapp.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieapp.API_KEY
import com.example.movieapp.repo.movie.MovieRepo
import com.example.movieapp.ui.isNetworkAvailable
import com.example.movieapp.ui.main.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MovieRepo,
    @ApplicationContext val context: Context
) : ViewModel() {

    val movieLiveData: MutableLiveData<ScreenState> = MutableLiveData()

    fun getMovies(apiKey: String, page: Int) {
        movieLiveData.postValue(ScreenState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            repo.getMovies(apiKey, page)
        }
    }

    fun getScrollingMovies() {
        if (isNetworkAvailable(context)) {
            if (repo.getResponsePage() < repo.getResponseLastPage()) {
                viewModelScope.launch(Dispatchers.IO) {
                    repo.getRemoteMovies(API_KEY, repo.getResponsePage() + 1)
                }
            }
        }
    }

    val items = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = true
        ), initialKey = 1
    ) {
        repo.getLocalMovies()
    }.flow.cachedIn(viewModelScope)
}