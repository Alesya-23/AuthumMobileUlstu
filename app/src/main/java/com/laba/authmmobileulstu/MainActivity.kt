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
            var mainFragment: MainFragment = MainFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.activity_main, mainFragment)
                .commit()
        }
    }

    fun openDisplayResultActivity(list: ArrayList<String>) {
        val intent = Intent(this, ResultSearchActivity::class.java)
        intent.putStringArrayListExtra("KEY_SEARCH_ELEMENT", list)
        startActivity(intent)
    }
}

