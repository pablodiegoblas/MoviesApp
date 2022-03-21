package com.example.wembleymoviesapp.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSqlite(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    private val sqlCreate: String = "CREATE TABLE ${Favourites.NAME}(" +
            "${Favourites.ID} INTEGER PRIMARY KEY," +
            "${Favourites.TITLE} TEXT," +
            "${Favourites.DESCRIPTION} TEXT," +
            "${Favourites.POSTER} TEXT," +
            "${Favourites.BACKDROP} TEXT," +
            "${Favourites.DATE} TEXT," +
            "${Favourites.VALUATION} DOUBLE," +
            "${Favourites.FAVOURITE} BOOLEAN)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(sqlCreate)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${Favourites.NAME}")
        db?.execSQL(sqlCreate)
    }
}