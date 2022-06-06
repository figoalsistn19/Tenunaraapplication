package com.example.tenunaraapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.tenunaraapplication.onboarding.OnBoardingActivity

class Splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val splashtime: Long = 2500

        Handler(Looper.getMainLooper()).postDelayed( {
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }, splashtime)

        supportActionBar?.hide()
    }
}