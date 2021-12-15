package com.laba.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import com.laba.authmmobileulstu.ItemList
import com.laba.authmmobileulstu.database.DatabaseHelper


const val TABLE = "Dance"
const val COLUMN_ID = "id"
const val COLUMN_NAME = "nameDance"
const val COLUMN_IS_MODERN = "isModernDance"

class DanceStorage(context: Context) {
    private var sqlHelper: DatabaseHelper = DatabaseHelper(context)
    private var db = sqlHelper.writableDatabase

    fun open(): DanceStorage {
        db = sqlHelper.writableDatabase
        return this
    }

    fun close() {
        db.close()
    }

    fun getFullList(): List<ItemList?> {
        val database = sqlHelper.readableDatabase
        val cursor: Cursor = database.rawQuery("select * from $TABLE", null)
        val list: MutableList<ItemList?> = ArrayList()
        if (!cursor.moveToFirst()) {
            return list
        }
        do {
            var id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            var isModern = cursor.getString(cursor.getColumnIndex(COLUMN_IS_MODERN))
            list.add(ItemList(id, name, isModern.toBoolean()))
            cursor.moveToNext()
        } while (!cursor.isAfterLast)
        cursor.close()
        database.close()
        return list
    }

    fun getElement(id: Int): ItemList? {
        val database = sqlHelper.readableDatabase
        val cursor: Cursor = database.rawQuery(
            "select * from " + TABLE + " where "
                    + COLUMN_ID + " = " + id, null
        )
        if (!cursor.moveToFirst()) {
            return null
        }
        var id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
        var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        var isModern = cursor.getString(cursor.getColumnIndex(COLUMN_IS_MODERN))
        cursor.close()
        database.close()
        return ItemList(id, name, isModern.toBoolean())
    }

    fun insert(model: ItemList) {
        val content = ContentValues()
        content.put(COLUMN_NAME, model.nameDance)
        content.put(COLUMN_IS_MODERN, model.isModernDance)
        val database = this.sqlHelper.writableDatabase
        database.insert(TABLE, null, content)
    }

    fun update(model: ItemList) {
        val content = ContentValues()
        content.put(COLUMN_NAME, model.nameDance)
        content.put(COLUMN_IS_MODERN, model.isModernDance)
        val where = COLUMN_ID + " = " + model.id
        val database = this.sqlHelper.writableDatabase
        database.update(TABLE, content, where, null)
    }

    fun delete(model: ItemList) {
        val database = sqlHelper.writableDatabase
        val where = COLUMN_ID + " = " + model.id
        database.delete(TABLE, where, null)
    }

    fun getElementCount(): Int {
        return DatabaseUtils.queryNumEntries(db, TABLE).toInt()
    }
}