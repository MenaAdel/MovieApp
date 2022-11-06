package com.example.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.model.Movie

@Dao
interface MovieAppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<Movie>)

    @Query("Select * from movie")
    fun getMovies(): List<Movie>
}