package com.laba.authmmobileulstu.database

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase

const val DATABASE_NAME = "Dance.db" // название бд
const val SCHEMA = 1 // версия базы данных

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, SCHEMA) {


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """CREATE TABLE IF NOT EXISTS dance (
    id integer PRIMARY KEY AUTOINCREMENT,
    nameDance character(100),
    isModernDance boolean );
    """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $DATABASE_NAME")
        onCreate(db)
    }

    fun delete(context: Context) {
        context.deleteDatabase(DATABASE_NAME)
    }
}