package com.example.wembleymoviesapp.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.wembleymoviesapp.data.model.MovieModel

class DBMoviesProvider(context: Context) {

    private val adminSqliteHelepr = AdminSqlite(context, "DBWembleyMovies.sqlite", null, 4)
    private val db: SQLiteDatabase = adminSqliteHelepr.writableDatabase

    /**
     * Function insert movies in database
     */
    fun insert(movie: MovieModel): Unit {

        db?.let {
            val newRegister = ContentValues()
            with(movie) {
                newRegister.put("ID", id)
                newRegister.put("overview", overview)
                newRegister.put("title", title)
                newRegister.put("poster", posterPath)
                newRegister.put("backdrop", backdropPath)
                newRegister.put("release_date", releaseDate)
                newRegister.put("popularity", popularity)
                newRegister.put("vote_average", voteAverage)
                newRegister.put("adult", adult)
            }

            it.insert("favourites", null, newRegister)
        }
    }

    /**
     * Function returns all favourites movies in database
     */
    fun getAllFavouritesMovies(): List<MovieModel> {
        val cursor: Cursor = db.rawQuery("select * from favourites", null)

        var favourites: MutableList<MovieModel> = ArrayList<MovieModel>()

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getInt(0)
                val overview = cursor.getString(1)
                val title = cursor.getString(2)
                val poster = cursor.getString(3)
                val backdrop = cursor.getString(4)
                val releaseDate = cursor.getString(5)
                val popularity = cursor.getDouble(6)
                val voteAverage = cursor.getDouble(7)
                val adult = cursor.getInt(8)

                val adultBoolean: Boolean = adult == 1

                val movie = MovieModel(
                    overview,
                    null,
                    title,
                    poster,
                    backdrop,
                    releaseDate,
                    popularity,
                    voteAverage,
                    id,
                    adultBoolean,
                    null
                )

                favourites.add(movie)

                cursor.moveToNext()
            }
        }

        return favourites
    }

    fun remove(id: Int?) {
        db.delete("favourites", "ID=$id", null)
    }

    fun destroy() {
        db.close()
    }

    /**
     * Function that check if the movie is in Database
     */
    fun checkExist(movieModel: MovieModel): Boolean {
        val listFav = getAllFavouritesMovies()

        if (movieModel in listFav) {
            return true
        }

        return false
    }

    /**
     * Function search movie by title in database
     */
    fun findMovie(title: String): MovieModel? {
        val cursor: Cursor = db.rawQuery("select * from favourites where title=\"$title\"", null)

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getInt(0)
                val overview = cursor.getString(1)
                val title = cursor.getString(2)
                val poster = cursor.getString(3)
                val backdrop = cursor.getString(4)
                val releaseDate = cursor.getString(5)
                val popularity = cursor.getDouble(6)
                val voteAverage = cursor.getDouble(7)
                val adult = cursor.getInt(8)

                val adultBoolean: Boolean = adult == 1

                val movie = MovieModel(
                    overview,
                    null,
                    title,
                    poster,
                    backdrop,
                    releaseDate,
                    popularity,
                    voteAverage,
                    id,
                    adultBoolean,
                    null
                )

                return movie
            }
        }

        return null
    }
}