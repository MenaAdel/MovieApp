package com.example.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.local.dao.MovieAppDao
import com.example.movieapp.model.Movie
import com.example.movieapp.model.TimeModel

@Database(entities = [Movie::class ,TimeModel::class] ,version = 4)
abstract class MovieAppDataBase: RoomDatabase() {

    abstract fun getMovieAppDao(): MovieAppDao
}