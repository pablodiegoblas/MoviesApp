package com.example.wembleymoviesapp.di

import android.app.Application
import com.example.wembleymoviesapp.data.database.AdminSqlite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    //Provide the adminSqlite
    @Singleton
    @Provides
    fun providesAdminSqlite(app: Application): AdminSqlite {
        return AdminSqlite(app, "DBWembleyMovies.sqlite", null, 6)
    }
}