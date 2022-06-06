package com.example.tenunaraapplication.register

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.ActivityRegisterBinding
import com.example.tenunaraapplication.login.ActivityLogin

class ActivityRegister : AppCompatActivity() {

    private lateinit var bindingRegister: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingRegister = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bindingRegister.root)

        setupView()

        bindingRegister.btnBack.setOnClickListener {
            val intent = Intent(this@ActivityRegister, ActivityLogin::class.java)
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