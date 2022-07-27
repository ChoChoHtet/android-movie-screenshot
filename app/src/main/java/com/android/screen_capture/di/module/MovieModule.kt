package com.android.screen_capture.di.module

import com.android.screen_capture.datasource.MovieRemoteDataSource
import com.android.screen_capture.datasource.impl.MovieRemoteDataSourceImpl
import com.android.screen_capture.repository.MovieRepository
import com.android.screen_capture.repository.impl.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class MovieModule {
    @Binds
    abstract fun movieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun movieRemoteDataSource(movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieRemoteDataSource
}