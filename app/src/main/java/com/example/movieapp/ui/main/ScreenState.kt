package com.example.movieapp.ui.main

sealed class ScreenState {
    object PagingLoading: ScreenState()
    object Loading: ScreenState()
    object Error: ScreenState()
}