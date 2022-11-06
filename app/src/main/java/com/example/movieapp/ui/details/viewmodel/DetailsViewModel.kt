package com.example.movieapp.ui.details.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movie
import com.example.movieapp.repo.movie.MovieRepo
import com.example.movieapp.ui.main.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel@Inject constructor(
    private val repo: MovieRepo
): ViewModel() {

    val movieLiveData: MutableLiveData<Movie?> = MutableLiveData()

    fun getMovieDetails(id: Int) {
        viewModelScope.launch (Dispatchers.IO){
           movieLiveData.postValue(repo.getMovieDetails(id))
        }
    }
}