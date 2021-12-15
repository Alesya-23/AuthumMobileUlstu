package com.laba.authmmobileulstu

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private val model: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            var chooseTypeStorage: FragmentChooseTypeStorage = FragmentChooseTypeStorage()
            supportFragmentManager.beginTransaction()
                .addToBackStack(FragmentChooseTypeStorage::class.simpleName)
                .replace(R.id.activity_main, chooseTypeStorage)
                .commit()
        }
    }


    fun openDisplayResultActivity(list: ArrayList<ItemList>) {
        val intent = Intent(this, ResultSearchActivity::class.java)
        intent.putExtra("KEY_SEARCH_ELEMENT", list)
        startActivity(intent)
    }
}

