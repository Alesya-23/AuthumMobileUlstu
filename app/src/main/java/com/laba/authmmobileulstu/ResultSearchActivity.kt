package com.laba.authmmobileulstu

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

const val KEY_SEARCH_ELEMENT = "KEY_SEARCH_ELEMENT"

class ResultSearchActivity : AppCompatActivity() {
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_seach)
        listView = findViewById(R.id.listview)
        val list = intent.getStringArrayListExtra(KEY_SEARCH_ELEMENT) as ArrayList<String>
        val arrayAdapter = ArrayAdapter(
            this.applicationContext, R.layout.list_item, list
        )
        listView.adapter = arrayAdapter
    }
}