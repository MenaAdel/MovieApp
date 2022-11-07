package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.data.local.dao.MovieAppDao
import com.example.movieapp.data.local.preference.Preferences
import com.example.movieapp.data.local.preference.PreferencesImp
import com.example.movieapp.data.local.services.MoviesCache
import com.example.movieapp.data.local.services.MoviesCacheImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CacheModule {

    @Singleton
    @Provides
    fun provideMoviesCache(dao: MovieAppDao): MoviesCache{
        return MoviesCacheImp(dao)
    }

    @Singleton
    @Provides
    fun providePreferenceCache(@ApplicationContext context: Context): Preferences{
        return PreferencesImp(context)
    }
}