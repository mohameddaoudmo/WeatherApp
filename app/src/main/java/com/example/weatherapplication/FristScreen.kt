package com.example.weatherapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView

class FristScreen : AppCompatActivity() {

    lateinit var viewModel :SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frist_screen)
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        val lottieView = findViewById<LottieAnimationView>(R.id.lottie_view)
        lottieView.setAnimation(R.raw.lottieweather)
        lottieView.playAnimation()

        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, dialog::class.java))
            finish() // Finish SplashActivity

        }, 3000) // Delay for 3 seconds
    }


}