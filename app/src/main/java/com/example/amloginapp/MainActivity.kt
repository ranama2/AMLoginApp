package com.example.amloginapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.amloginapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnSubmit.setOnClickListener {

            val submittedEmail = binding.etEmail.text.toString()
            val submittedPassword = binding.etPassword.text.toString()

            if (submittedEmail.isNotEmpty() && submittedPassword.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(submittedEmail, submittedPassword).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this, WelcomeUserActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Credentials doesn't match! Please enter valid username and password or signup to create an account", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvSignUpBelowSubmit.setOnClickListener{
            val signupIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signupIntent)
        }
    }

    private fun isValidPassword(password: String?): Boolean {
        val matcher: Matcher
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        val pattern: Pattern = Pattern.compile(passwordPattern)
        matcher = pattern.matcher(password.toString())
        return matcher.matches()
    }
}