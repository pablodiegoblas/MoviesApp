package com.example.mymoviesapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymoviesapp.data.database.entities.GenreEntity
import retrofit2.http.DELETE

@Dao
interface GenresDao {

    @Query("SELECT * FROM genres_table WHERE selected = '1'")
    suspend fun getSelectedGenres(): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Query("DELETE FROM genres_table")
    suspend fun deleteGenres()
}