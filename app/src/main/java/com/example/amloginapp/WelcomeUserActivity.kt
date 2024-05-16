package com.example.amloginapp

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.amloginapp.databinding.ActivitySignupBinding
import com.example.amloginapp.databinding.ActivityWelcomeUserBinding

class WelcomeUserActivity: AppCompatActivity() {

    lateinit var binding: ActivityWelcomeUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = findViewById<EditText>(R.id.etSignUpUsername)
       // findViewById<TextView>(R.id.tvUser) = username






    }


}