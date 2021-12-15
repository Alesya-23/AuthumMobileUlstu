package com.laba.authmmobileulstu.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.laba.authmmobileulstu.MainActivity
import com.laba.authmmobileulstu.R
import java.util.*

class SplashActivity : AppCompatActivity(){
    var button_list: LinkedList<ToggleButton> = LinkedList()
    var first_button = 0
    var second_button:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        first_button = (Math.random() * 7).toInt() + 1
        second_button = (Math.random() * 8).toInt() + 1
        while (first_button == second_button) {
            second_button = (Math.random() * 8).toInt() + 1
        }
        if (first_button > second_button) {
            val temp = second_button
            second_button = first_button
            first_button = temp
        }
      init()
    }

    private fun init(){
        var toggleButtonOne = findViewById<ToggleButton>(R.id.toggleButtonOne)
        var toggleButtonTwo = findViewById<ToggleButton>(R.id.toggleButtonTwo)
        var toggleButtonThree = findViewById<ToggleButton>(R.id.toggleButtonThree)
        var toggleButtonFour = findViewById<ToggleButton>(R.id.toggleButtonFour)
        var toggleButtonFive = findViewById<ToggleButton>(R.id.toggleButtonFive)
        var toggleButtonSix = findViewById<ToggleButton>(R.id.toggleButtoSix)
        var toggleButtonSeven = findViewById<ToggleButton>(R.id.toggleButtonSeven)
        var toggleButtonEight = findViewById<ToggleButton>(R.id.toggleButtonEight)
        button_list.add(toggleButtonOne)
        button_list.add(toggleButtonTwo)
        button_list.add(toggleButtonThree)
        button_list.add(toggleButtonFour)
        button_list.add(toggleButtonFive)
        button_list.add(toggleButtonSix)
        button_list.add(toggleButtonSeven)
        button_list.add(toggleButtonEight)
        resetValue()
        var textViewTask = findViewById<TextView>(R.id.textViewTask)
        textViewTask.text = "Найдите танцора"
    }

    private fun resetValue() {
        var index = 1
        for (button in button_list!!) {
            button.textOff = " $index"
            if (index == first_button || index == second_button) {
                button.textOn = "Майкл Джексон"
            } else {
                button.textOn = "Владимир Путин"
            }
            button.text = " $index"
            button.setOnClickListener { v: View? -> check() }
            index++
        }
    }

    private fun check() {
        var index = 1
        for (button in button_list!!) {
            if (index == second_button || index == first_button) {
                if (!button.isChecked) {
                    return
                }
            }
            index++
        }
        index = 1
        for (button in button_list!!) {
            if (index != second_button && index != first_button) {
                if (button.isChecked) {
                    return
                }
            }
            index++
        }
        openSettingsActivity()
    }


    private fun openSettingsActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}