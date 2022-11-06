package com.example.movieapp.di

import com.example.movieapp.data.remote.MovieAppService
import com.example.movieapp.data.remote.service.ApiHelper
import com.example.movieapp.data.remote.service.ApiHelperImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideMoviesApi(api: MovieAppService): ApiHelper {
        return ApiHelperImp(api)
    }
}