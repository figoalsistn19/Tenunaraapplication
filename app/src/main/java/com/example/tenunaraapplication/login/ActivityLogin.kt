package com.example.tenunaraapplication.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tenunaraapplication.databinding.ActivityLoginBinding
import com.example.tenunaraapplication.main.ui.MainActivity
import com.example.tenunaraapplication.register.ActivityRegister
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ActivityLogin : AppCompatActivity() {

    private lateinit var bindingLogin: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLogin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLogin.root)

        setupView()

        auth = Firebase.auth

        bindingLogin.tvSignupIntent.setOnClickListener {
            val intent = Intent(this@ActivityLogin, ActivityRegister::class.java)
            startActivity(intent)
        }

        bindingLogin.loginButton.setOnClickListener {
            val intent = Intent(this@ActivityLogin, MainActivity::class.java)
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