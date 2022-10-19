package com.example.mymoviesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymoviesapp.data.database.dao.GenresDao
import com.example.mymoviesapp.data.database.dao.MoviesDao
import com.example.mymoviesapp.data.database.entities.GenreEntity
import com.example.mymoviesapp.data.database.entities.MovieEntity

@Database(entities = [MovieEntity::class, GenreEntity::class], version = 5)
abstract class MoviesDatabase: RoomDatabase() {

    abstract fun getMovieDao(): MoviesDao

    abstract fun getGenreDao(): GenresDao
}