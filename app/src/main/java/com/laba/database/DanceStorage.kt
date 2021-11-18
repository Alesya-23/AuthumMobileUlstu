package com.laba.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor

import android.database.sqlite.SQLiteDatabase
import com.laba.authmmobileulstu.ItemList
import com.laba.authmmobileulstu.database.DatabaseHelper
import java.lang.Boolean


const val TABLE = "Dance"
const val COLUMN_ID = "Id"
const val COLUMN_NAME = "nameDance"
const val COLUMN_IS_MODERN = "isModernDance"

class DanceStorage {
    var sqlHelper: DatabaseHelper? = null
    var db: SQLiteDatabase? = null

    fun open(): DanceStorage? {
        db = sqlHelper?.writableDatabase
        return this
    }

    fun close() {
        db!!.close()
    }

    @SuppressLint("Recycle")
    fun getFullList(): List<ItemList?>? {
        val cursor: Cursor = db!!.rawQuery("select * from $TABLE", null)
        val list: MutableList<ItemList?> = ArrayList()
        if (!cursor.moveToFirst()) {
            return list
        }
        do {
            val obj:ItemList = ItemList(1, "fvdf", false)
            obj.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID) as Int)
            obj.nameDance = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            obj.isModernDance = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(
                COLUMN_IS_MODERN
            )))
            list.add(obj)
            cursor.moveToNext()
        } while (!cursor.isAfterLast)
        return list
    }

    @SuppressLint("Recycle")
    fun getFilteredList(model: ItemList?): List<ItemList?>? {
        val cursor: Cursor = db!!.rawQuery("select * from $TABLE", null)
        val list: MutableList<ItemList?> = ArrayList()
        if (!cursor.moveToFirst()) {
            return list
        }
        do {
            var obj:ItemList = ItemList(1, "fvdf", false)
            obj.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID) as Int)
            obj.nameDance = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            obj.isModernDance = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(
                COLUMN_IS_MODERN
            )))
            list.add(obj)
            cursor.moveToNext()
        } while (!cursor.isAfterLast)
        return list
    }

    fun getElement(model: ItemList): ItemList? {
        val cursor: Cursor = db!!.rawQuery(
            "select * from " + TABLE + " where "
                    + COLUMN_ID + " = " + model.id, null
        )
        val obj:ItemList = ItemList(1, "fvdf", false)
        if (!cursor.moveToFirst()) {
            return null
        }
        obj.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID) as Int)
        obj.nameDance = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        obj.isModernDance = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(
            COLUMN_IS_MODERN
        )))
        return obj
    }

    fun insert(model: ItemList) {
        val content = ContentValues()
        content.put(COLUMN_ID, model.id)
        content.put(COLUMN_IS_MODERN, model.isModernDance)
        content.put(COLUMN_NAME, model.nameDance)
        db?.insert(TABLE, null, content)
    }

    fun update(model: ItemList) {
        val content = ContentValues()
        content.put(COLUMN_ID, model.id)
        content.put(COLUMN_IS_MODERN, model.isModernDance)
        content.put(COLUMN_NAME, model.nameDance)
        val where = COLUMN_ID + " = " + model.id
        db?.update(TABLE, content, where, null)
    }

    fun delete(model: ItemList) {
        val where = COLUMN_ID + " = " + model.id
        db?.delete(TABLE, where, null)
    }
}