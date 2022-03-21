package com.example.wembleymoviesapp.data.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.wembleymoviesapp.domain.GetMoviesDB
import javax.inject.Inject

class DBMoviesProvider @Inject constructor() {

    @Inject
    lateinit var adminSqliteHelper: AdminSqlite
    private lateinit var db: SQLiteDatabase

    private val favouriteNum = 1
    private val noFavouriteNum = 0

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
                val newRegister = ContentValues().apply {
                    with(movie) {
                        put(Favourites.ID, id)
                        put(Favourites.TITLE, title)
                        put(Favourites.DESCRIPTION, overview)
                        put(Favourites.POSTER, poster)
                        put(Favourites.BACKDROP, backdrop)
                        put(Favourites.DATE, releaseDate)
                        put(Favourites.VALUATION, voteAverage)
                        put(Favourites.FAVOURITE, favourite)
                    }
                }


                db.insert(Favourites.NAME, null, newRegister)
            }
        }
    }

    /**
     * Function returns all favourites movies in database
     */
    fun getAllFavouritesMovies(result: GetMoviesDB) {
        val cursor: Cursor = db.rawQuery(
            "select * from ${Favourites.NAME} WHERE ${Favourites.FAVOURITE} = '$favouriteNum'",
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

        result.onSuccess(favourites)
    }

    /**
     * This movie not favorite yet
     */
    fun removeFavourite(id: Int?) {
        db.execSQL(
            "UPDATE ${Favourites.NAME}" +
                    " SET ${Favourites.FAVOURITE} = '$noFavouriteNum'" +
                    " WHERE ${Favourites.ID}='$id'"
        )
    }

    /**
     * This movie is favourite now
     */
    fun setFavourite(id: Int) {
        db.execSQL(
            "UPDATE ${Favourites.NAME}" +
                    " SET ${Favourites.FAVOURITE}='$favouriteNum'" +
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