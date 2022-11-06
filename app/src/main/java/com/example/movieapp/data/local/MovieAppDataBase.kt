package com.example.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.local.dao.MovieAppDao
import com.example.movieapp.model.Movie

@Database(entities = [Movie::class] ,version = 1)
abstract class MovieAppDataBase: RoomDatabase() {

    abstract fun getMovieAppDao(): MovieAppDao
}