package com.laba.authmmobileulstu.splash

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.laba.authmmobileulstu.R


class SmileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(Smile(this))
        Handler().postDelayed({
            val intent: Intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
        }, 4000)
    }

    internal class Smile(context: Context?) : View(context) {
        var p: Paint? = Paint()
        override fun onDraw(canvas: Canvas) {
            canvas.drawRGB(255, 120, 14)
            // Настройка кисти
            p?.color = Color.argb(255, 255, 165, 61)
            // Толщина линии = 30
            p?.strokeWidth = 30F
            // Рисуем круг с центром в (350, 600), радиус = 300
            p?.let { canvas.drawCircle(400F, 650F, 260F, it) }
            // Настройка кисти для точек и линии
            canvas.drawLine(200F, 500F, 280F, 300F, p!!)
            canvas.drawLine(280F, 310F, 350F, 480F, p!!)
            canvas.drawLine(450F, 500F, 480F, 300F, p!!)
            canvas.drawLine(480F, 310F, 590F, 480F, p!!)
            p!!.color = Color.WHITE
            p?.let { canvas.drawCircle(460F, 550F, 50F, it) }
            p?.let { canvas.drawCircle(360F, 550F, 50F, it) }
            p!!.color = Color.BLACK
            p?.let { canvas.drawCircle(450F, 570F, 20F, it) }
            p?.let { canvas.drawCircle(370F, 570F, 20F, it) }
            p!!.color = Color.MAGENTA
            p?.strokeWidth = 15F
            canvas.drawLine(350F, 650F, 450F, 650F, p!!)
            canvas.drawLine(450F, 650F, 410F, 700F, p!!)
            canvas.drawLine(410F, 700F, 350F, 650F, p!!)
            p!!.color = Color.BLACK
            p?.strokeWidth = 10F
            canvas.drawLine(250F, 640F, 150F, 630F, p!!)
            canvas.drawLine(250F, 650F, 110F, 650F, p!!)
            canvas.drawLine(250F, 650F, 150F, 710F, p!!)
            canvas.drawLine(550F, 640F, 650F, 630F, p!!)
            canvas.drawLine(550F, 650F, 700F, 650F, p!!)
            canvas.drawLine(550F, 650F, 650F, 710F, p!!)
        }
    }
}

