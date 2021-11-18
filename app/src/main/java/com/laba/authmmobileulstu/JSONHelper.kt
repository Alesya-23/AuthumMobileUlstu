package com.laba.authmmobileulstu

import android.content.Context
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*


class JSONHelper {
    private val  fileName: String = "user.json"
    fun exportToJSON(context: Context, dataList: ArrayList<ItemList>): Boolean {
        val gson = Gson()
        val jsonString = gson.toJson(dataList)
        try {
            val fos: FileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            fos.write(jsonString.toByteArray())
            fos.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    private fun loadJSONFromAsset(context: Context): String {
        var json: String = ""
        try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }

    fun loadDataFromJson(context: Context): ArrayList<ItemList>? {
        try {
            val jsonObject = JSONObject(loadJSONFromAsset(context))
            val jsonArray: JSONArray = jsonObject.getJSONArray("ItemLists")
            var dataItems: ArrayList<ItemList> = ArrayList<ItemList>()
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val id = item.getInt("ID")
                val nameDance = item.getString("NameItemList")
                val isModernDance = item.getBoolean("isModernDance")
                dataItems.add(ItemList(id, nameDance, isModernDance))
            }
            return dataItems
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    fun deleteFile() {
        val fdelete: File = File(fileName)
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                println("file Deleted :$fileName")
            } else {
                println("file not Deleted :$fileName")
            }
        }
    }

}