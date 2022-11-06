package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.data.local.services.MoviesCache
import com.example.movieapp.data.remote.service.ApiHelper
import com.example.movieapp.repo.movie.MovieRepo
import com.example.movieapp.repo.movie.MovieRepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideMoviesRepo(
        moviesCache: MoviesCache,
        apiHelper: ApiHelper,
        @ApplicationContext context: Context
    ): MovieRepo {
        return MovieRepoImp(moviesCache, apiHelper ,context)
    }
}