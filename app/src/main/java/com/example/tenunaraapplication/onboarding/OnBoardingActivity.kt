package com.example.tenunaraapplication.onboarding

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.example.tenunaraapplication.databinding.ActivityOnboardingBinding
import com.example.tenunaraapplication.login.ActivityLogin

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var bindingOnBoarding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingOnBoarding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(bindingOnBoarding.root)


        setupView()

        bindingOnBoarding.button.setOnClickListener {
            val intent = Intent(this@OnBoardingActivity, ActivityLogin::class.java)
            startActivity(intent)
        }

    }



    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}