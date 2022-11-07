package com.example.movieapp.repo.movie

import com.example.movieapp.TIME_TO_UPDATE
import kotlin.math.abs


fun canUpdateDataUseCase(savedTime: Int?, currentTime: Int) = abs(((savedTime ?: 0) - currentTime)) >= TIME_TO_UPDATE