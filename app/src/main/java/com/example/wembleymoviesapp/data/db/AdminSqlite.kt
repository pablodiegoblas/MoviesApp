package com.example.wembleymoviesapp.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSqlite(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    private val SQL_CREATE: String = "CREATE TABLE favourites(ID INTEGER PRIMARY KEY," +
            "overview TEXT," +
            "title TEXT," +
            "poster TEXT," +
            "backdrop TEXT," +
            "release_date TEXT," +
            "popularity DOUBLE," +
            "vote_average DOUBLE," +
            "adult INT)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS favourites")
        db?.execSQL(SQL_CREATE)
    }
}