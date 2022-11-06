package com.example.movieapp.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.repo.movie.MovieRepo
import com.example.movieapp.ui.main.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MovieRepo
) : ViewModel() {

    val movieLiveData: MutableLiveData<ScreenState> = MutableLiveData()

    fun getMovies(apiKey: String, page: Int) {
        movieLiveData.postValue(ScreenState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val movies = repo.getMovies(apiKey, page)
            if (movies.isNullOrEmpty()) {
                movieLiveData.postValue(ScreenState.Error)
            } else {
                movieLiveData.postValue(ScreenState.Success(movies))
            }
        }
    }
}