package com.laba.authmmobileulstu

import android.content.Context
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStreamReader

class JSONHelper {
    val FILE_NAME = "C:\\Users\\aleca\\user.json"
    fun exportToJSON(context: Context, dataList: ArrayList<ItemList>): Boolean {
        val gson = Gson()
        val dataItems = DataItems()
        dataItems.setUsers(dataList)
        val jsonString = gson.toJson(dataItems)
        try {
            context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { fileOutputStream ->
                fileOutputStream.write(jsonString.toByteArray())
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun importFromJSON(context: Context): ArrayList<ItemList>? {
        try {
            context.openFileInput(FILE_NAME).use { fileInputStream ->
                InputStreamReader(fileInputStream).use { streamReader ->
                    val gson = Gson()
                    val dataItems: DataItems = gson.fromJson(streamReader, DataItems::class.java)
                    return dataItems.getUsers()
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return null
    }

    private class DataItems {
        private var users: ArrayList<ItemList>? = null

        fun getUsers(): ArrayList<ItemList>? {
            return users
        }

        fun setUsers(users: ArrayList<ItemList>?) {
            this.users = users
        }
    }
}