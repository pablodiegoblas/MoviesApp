package com.example.wembleymoviesapp.di

import com.example.wembleymoviesapp.MoviesApp
import com.example.wembleymoviesapp.data.database.AdminSqlite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesAdminSqlite(): AdminSqlite{
        return AdminSqlite(MoviesApp.instance, "DBWembleyMovies.sqlite", null, 6)
    }
}