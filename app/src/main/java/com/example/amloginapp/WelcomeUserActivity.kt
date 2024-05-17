package com.example.amloginapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.amloginapp.databinding.ActivitySignupBinding
import com.example.amloginapp.databinding.ActivityWelcomeUserBinding
import com.google.firebase.auth.FirebaseAuth

class WelcomeUserActivity: AppCompatActivity() {

    lateinit var binding: ActivityWelcomeUserBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val email = intent.getStringExtra("email")
        binding.tvWelcomeUser.text = email



        binding.btnWelcomeLogout.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))

        }








    }


}