package com.example.mymoviesapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster") val poster: String?,
    @ColumnInfo(name = "backdrop") val backdrop: String?,
    @ColumnInfo(name = "releaseDate") val releaseDate: String,
    @ColumnInfo(name = "voteAverage") val voteAverage: Double?,
    @ColumnInfo(name = "favourite") val favourite: Boolean,
    @ColumnInfo(name = "state") val movieState: Int? = 0
)