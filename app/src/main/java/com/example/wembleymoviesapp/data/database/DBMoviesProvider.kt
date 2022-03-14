package com.example.wembleymoviesapp.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DBMoviesProvider(context: Context) {

    private val adminSqliteHelper = AdminSqlite(context, "DBWembleyMovies.sqlite", null, 6)
    private lateinit var db: SQLiteDatabase

    private val FAVOURITE_NUM = 1
    private val NO_FAVOURITE_NUM = 0

    fun openDB() {
        db = adminSqliteHelper.writableDatabase
    }

    fun closeDatabase() {
        db.close()
    }

    /**
     * Function insert movies in database
     */
    fun insert(listMovieDB: List<MovieDB>) {

        for (movie in listMovieDB) {
            val isMovieSearch: Boolean = findMovie(movie.id)?.id == movie.id

            if (!isMovieSearch) {
                val newRegister = ContentValues()
                with(movie) {
                    newRegister.put(Favourites.ID, id)
                    newRegister.put(Favourites.TITLE, title)
                    newRegister.put(Favourites.DESCRIPTION, overview)
                    newRegister.put(Favourites.POSTER, poster)
                    newRegister.put(Favourites.BACKDROP, backdrop)
                    newRegister.put(Favourites.DATE, releaseDate)
                    newRegister.put(Favourites.VALUATION, voteAverage)
                    newRegister.put(Favourites.FAVOURITE, favourite)
                }

                db.insert(Favourites.NAME, null, newRegister)
            } else println("THIS MOVIE ${movie.title} EXISTS YET")

        }

    }

    /**
     * Function returns all favourites movies in database
     */
    fun getAllFavouritesMovies(): MutableList<MovieDB> {
        val cursor: Cursor = db.rawQuery(
            "select * from ${Favourites.NAME} WHERE ${Favourites.FAVOURITE} = '$FAVOURITE_NUM'",
            null
        )

        //Important in Kotlin use mutableListOf or listOf
        val favourites: MutableList<MovieDB> = mutableListOf()

        // Implementing this method, the cursor closes itself
        cursor.use {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val id = cursor.getInt(0)
                    val title = cursor.getString(1)
                    val overview = cursor.getString(2)
                    val poster = cursor.getString(3)
                    val backdrop = cursor.getString(4)
                    val releaseDate = cursor.getString(5)
                    val voteAverage = cursor.getDouble(6)
                    val favourite = cursor.getInt(7)

                    val favouriteBoolean: Boolean = favourite == 1

                    val movie = MovieDB(
                        id,
                        title,
                        overview,
                        poster,
                        backdrop,
                        releaseDate,
                        voteAverage,
                        favouriteBoolean
                    )

                    favourites.add(movie)

                    cursor.moveToNext()
                }
            }
        }

        return favourites
    }

    /**
     * This movie not favorite yet
     */
    fun removeFavourite(id: Int?) {
        db.execSQL(
            "UPDATE ${Favourites.NAME}" +
                    " SET ${Favourites.FAVOURITE} = '$NO_FAVOURITE_NUM'" +
                    " WHERE ${Favourites.ID}='$id'"
        )
    }

    /**
     * This movie is favourite now
     */
    fun setFavourite(id: Int) {
        db.execSQL(
            "UPDATE ${Favourites.NAME}" +
                    " SET ${Favourites.FAVOURITE}='$FAVOURITE_NUM'" +
                    " WHERE ${Favourites.ID}='$id'"
        )
    }

    /**
     * Function search movie by title in database
     */
    fun findMovie(id: Int): MovieDB? {
        val cursor: Cursor =
            db.rawQuery("select * from ${Favourites.NAME} where ${Favourites.ID}='$id'", null)

        cursor.use {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val idDB = cursor.getInt(0)
                    val title = cursor.getString(1)
                    val overview = cursor.getString(2)
                    val poster = cursor.getString(3)
                    val backdrop = cursor.getString(4)
                    val releaseDate = cursor.getString(5)
                    val voteAverage = cursor.getDouble(6)
                    val favourite = cursor.getInt(7)

                    val favouriteBoolean: Boolean = favourite == 1

                    return MovieDB(
                        idDB,
                        title,
                        overview,
                        poster,
                        backdrop,
                        releaseDate,
                        voteAverage,
                        favouriteBoolean
                    )
                }
            }
        }

        return null
    }
}