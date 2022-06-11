@file:Suppress("DEPRECATION")

package com.example.tenunaraapplication.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tenunaraapplication.databinding.ActivityRegisterBinding
import com.example.tenunaraapplication.login.ActivityLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ActivityRegister : AppCompatActivity() {

    private lateinit var bindingRegister: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingRegister = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bindingRegister.root)

        setupView()

        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("USERS")

        bindingRegister.btnBack.setOnClickListener {
            val intent = Intent(this@ActivityRegister, ActivityLogin::class.java)
            startActivity(intent)
        }

        bindingRegister.registerButton.setOnClickListener {
            val userName = bindingRegister.usernameEditText.text.toString()
            val email = bindingRegister.emailET.text.toString()
            val password = bindingRegister.passwordET.text.toString()

            if (userName.isEmpty()) {
                bindingRegister.usernameEditText.error = "Username required"
                bindingRegister.usernameEditText.requestFocus()
                return@setOnClickListener
            }

            registerFirebase(email, password, userName)
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

    private fun registerFirebase(email: String, password: String, userName: String) {
        val progressDialog = ProgressDialog(this@ActivityRegister)
        progressDialog.setTitle("Registratation User")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    saveUser(userName, email, progressDialog)
                } else {
                    val message = it.exception!!.toString()
                    Toast.makeText(this, "Failed to register : $message", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUser(userName: String,
                         email: String,
                         progressDialog: ProgressDialog
    ){
        val currentUserId = auth.currentUser!!.uid
        ref = FirebaseDatabase.getInstance().reference.child("USERS")
        val userMap = HashMap<String,Any>()
        userMap["id"] = currentUserId
        userMap["email"] = email
        userMap["userName"] = userName
        userMap["phoneNumber"] = ""

        ref.child(currentUserId).setValue(userMap).addOnCompleteListener {
            if(it.isSuccessful){
                progressDialog.dismiss()
                Toast.makeText(this, "Register is Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ActivityRegister, ActivityLogin::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }else{
                val message = it.exception!!.toString()
                Toast.makeText(this, "Error : $message", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }



    }
}