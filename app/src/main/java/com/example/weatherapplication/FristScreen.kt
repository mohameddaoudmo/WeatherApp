package com.example.weatherapplication

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class FristScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frist_screen)
        val splashThread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(3000) // Sleep for 3 seconds
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {

                    startActivity(Intent(this@FristScreen, MainActivity::class.java))
                    finish() // Finish SplashActivity
                }
            }
        }
        splashThread.start()


    }
}