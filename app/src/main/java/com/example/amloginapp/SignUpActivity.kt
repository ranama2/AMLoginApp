package com.example.amloginapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.amloginapp.databinding.ActivityMainBinding
import com.example.amloginapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignUpActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnSignUpSubmit.setOnClickListener{
            val submittedUsername = binding.etSignUpUsername.text.toString()
            val submittedEmail = binding.etSignUpEmail.text.toString()
            val submittedPassword = binding.etSignUpPassword.text.toString()
            val submittedPasswordConfirm = binding.etSignUpPasswordConfirm.text.toString()




            if (submittedUsername.isEmpty()){
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(submittedEmail).matches()) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            } else if (!isValidPassword(submittedPassword)){
                Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT).show()
            } else if (submittedPassword != submittedPasswordConfirm){
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
            } else {

                firebaseAuth.createUserWithEmailAndPassword(submittedEmail, submittedPassword).addOnCompleteListener{

                    if (it.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.tvAlreadyUser.setOnClickListener{
            val loginIntent = Intent(this, MainActivity::class.java)
            startActivity(loginIntent)
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