package com.example.movieapp.ui.main

import com.example.movieapp.model.Movie

sealed class ScreenState {
    class Success(val list: List<Movie>): ScreenState()
    object Loading: ScreenState()
    object Error: ScreenState()
}