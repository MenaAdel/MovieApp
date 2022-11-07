package com.example.movieapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieResponse
import com.example.movieapp.model.TimeModel

@Dao
interface MovieAppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<Movie>)

    @Query("Select * from movie")
    fun getMovies(): PagingSource<Int, Movie>

    @Query("Delete from movie")
    fun deleteMovies()


    @Query("Select * from movie where id=:id")
    fun getMovieDetails(id: Int): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTime(time: TimeModel)

    @Query("delete from time")
    fun deleteTime()

    @Query("select time from time")
    fun getTime(): Int?
}