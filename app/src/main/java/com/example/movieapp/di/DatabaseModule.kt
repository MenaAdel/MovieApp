package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.local.dao.MovieAppDao
import com.example.movieapp.data.local.MovieAppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieAppDataBase{
        return Room.databaseBuilder(context, MovieAppDataBase::class.java, "movie_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMovieAppDao(db: MovieAppDataBase): MovieAppDao {
        return db.getMovieAppDao()
    }
}