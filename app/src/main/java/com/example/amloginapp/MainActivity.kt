package com.example.amloginapp

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.amloginapp.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient



    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()

        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption)

        binding.btnSigninWithGoogle.setOnClickListener {
            signInGoogle()
        }
    }

    private fun signInGoogle() {
        val googleSignInIntent = googleSignInClient.signInIntent
        launcher.launch(googleSignInIntent)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResult(task)
            }

    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account: GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
    } else
    {
        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
    }





























//        firebaseAuth = FirebaseAuth.getInstance()
//        val context = this
//        val coroutineScope = rememberCoroutineScope()
//
//        binding.btnSigninWithGoogle.setOnClickListener {
//
//            val email = binding.etEmail.text.toString()
//            val password = binding.etPassword.text.toString()
//            val credentialManager = CredentialManager.create(context)
//
//            val rawNonce = UUID.randomUUID().toString()
//            val bytes = rawNonce.toByteArray()
//


//            val md = MessageDigest.getInstance("SHA-256")
//            val digest = md.digest(bytes)
//            val hashedNonce = digest.fold("") {str, it -> str + "%02x".format(it)}
//            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
//                .setFilterByAuthorizedAccounts(false)
//                .setServerClientId("1056189690250-79auaji7dq3s68ujutumdnqh2lc227q7.apps.googleusercontent.com")
//                .setNonce(hashedNonce)
//                .build()
//
//            val request: GetCredentialRequest = GetCredentialRequest.Builder()
//                .addCredentialOption(googleIdOption)
//                .build()
//
//            coroutineScope.launch{
//                try {
//                val result = credentialManager.getCredential(
//                    request = request,
//                    context = context,
//                )
//                val credential = result.credential
//
//                val googleIdTokenCredential = GoogleIdTokenCredential
//                    .createFrom(credential.data)
//
//                val googleIdToken = googleIdTokenCredential.idToken
//
//                Log.i(TAG, googleIdToken)
//                Toast.makeText(context, "You are signed in!", Toast.LENGTH_SHORT).show()
//
//            } catch (e: GetCredentialException){
//                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
//
//                } catch (e: GoogleIdTokenParsingException){
//                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//
//        }

//
//        firebaseAuth = FirebaseAuth.getInstance()
//        binding.btnSubmit.setOnClickListener {
//
//            val submittedEmail = binding.etEmail.text.toString()
//            val submittedPassword = binding.etPassword.text.toString()
//
//            if (submittedEmail.isNotEmpty() && submittedPassword.isNotEmpty()){
//                firebaseAuth.signInWithEmailAndPassword(submittedEmail, submittedPassword).addOnCompleteListener{
//                    if (it.isSuccessful){
//                        val intent = Intent(this, WelcomeUserActivity::class.java)
//                        startActivity(intent)
//                    } else {
//                        Toast.makeText(this, "Credentials doesn't match! Please enter valid username and password or signup to create an account", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            } else {
//                Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//
//
//        binding.tvSignUpBelowSubmit.setOnClickListener{
//            val signupIntent = Intent(this, SignUpActivity::class.java)
//            startActivity(signupIntent)
//        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful){
                val intent: Intent = Intent(this, WelcomeUserActivity::class.java)
                intent.putExtra("email", account.email)
                startActivity(intent)
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }

//    override fun onStart() {
//        super.onStart()
//
//        if (firebaseAuth.currentUser != null){
//            val intent = Intent(this, WelcomeUserActivity::class.java)
//            startActivity(intent)
//        }
//    }

    private fun isValidPassword(password: String?): Boolean {
        val matcher: Matcher
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        val pattern: Pattern = Pattern.compile(passwordPattern)
        matcher = pattern.matcher(password.toString())
        return matcher.matches()
    }
}

