package com.example.wembleymoviesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wembleymoviesapp.data.database.dao.MoviesDao
import com.example.wembleymoviesapp.data.database.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 2)
abstract class MoviesDatabase: RoomDatabase() {

    abstract fun getMovieDao(): MoviesDao
}