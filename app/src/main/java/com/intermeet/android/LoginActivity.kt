package com.intermeet.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        // Initializing firebase auth
        auth = FirebaseAuth.getInstance()

        val emailEditText: EditText = findViewById(R.id.signInEmail)
        val passwordEditText: EditText = findViewById(R.id.signInPassword)
        val loginButton: TextView = findViewById(R.id.loginButton)
        val signUpButton: Button = findViewById(R.id.signUpButton)
        val forgotPasswordButton: TextView = findViewById(R.id.forgotPassword)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            logIn(email, password)
        }

        signUpButton.setOnClickListener {
            // Intent to navigate to the SecondActivity
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        forgotPasswordButton.setOnClickListener {
            // Intent to navigate to the SecondActivity
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logIn(email: String, password: String) {
        try {
            Log.d("LoginActivity", "Attempting to log in with email: $email")
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
            } else {
                Toast.makeText(this, "Invalid Email and Password.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error during login: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        Log.d("LoginActivity", "Updating UI, user is ${if (user == null) "null" else "not null"}")
        if (user != null) {
            // User is signed in
            val intent = Intent(this, MainActivity::class.java) // sends to MainActivity.js
            startActivity(intent)
            finish()
        } else {
            // User is signed out
            Toast.makeText(baseContext, "Please sign in to continue.", Toast.LENGTH_SHORT).show()
        }
    }

//    private
//    fun buttonFunc() {
//        signUpButton.setOnClickListener {
//            // Intent to navigate to the SecondActivity
//            val intent = Intent(this, SignupActivity::class.java)
//            startActivity(intent)
//        }
//    }
}